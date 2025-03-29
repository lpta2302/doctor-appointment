package com.nhom1.shift_service.core.shift.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nhom1.shift_service.common.PageResponse;
import com.nhom1.shift_service.core.doctor.client.DoctorClient;
import com.nhom1.shift_service.core.doctor.dto.DoctorResponse;
import com.nhom1.shift_service.core.schedule.entity.Schedule;
import com.nhom1.shift_service.core.shift.dto.ShiftRequest;
import com.nhom1.shift_service.core.shift.dto.ShiftResponse;
import com.nhom1.shift_service.core.shift.entity.Shift;
import com.nhom1.shift_service.core.shift.mapper.ShiftMapper;
import com.nhom1.shift_service.core.shift.repository.ShiftRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShiftService {

    private final ShiftRepository shiftRepository;
    private final ShiftMapper shiftMapper;
    private final DoctorClient doctorClient;

    public List<Shift> createShifts(List<ShiftRequest> shiftRequests) {
        validateShifts(shiftRequests);
        final NavigableSet<Shift> newShifts = new TreeSet<>(
            (s1, s2) -> s1.getStartTime().compareTo(s2.getStartTime())
        );

        shiftRequests.forEach(shiftRequest->{
            Shift newShift = Shift.builder()
                .doctorId(shiftRequest.doctorId())
                .startTime(shiftRequest.startTime())
                .endTime(shiftRequest.endTime())
                .build();

            
            Shift lower = newShifts.lower(newShift);
            Shift higher = newShifts.higher(newShift);

            if ((lower != null && isOverlappingShift(newShift, lower)) ||
                (higher != null && isOverlappingShift(newShift, higher))) {
                throw new IllegalArgumentException("Overlapping shift start at " + newShift.getStartTime());
            }

            newShifts.add(newShift);
        });

        return newShifts.stream().toList();
    }

    public void addShift(Schedule schedule, Shift newShift) {
        validateShift(newShift);
        List<Shift> shifts = schedule.getShifts();
        if (shifts == null || shifts.isEmpty()) {
            schedule.addShift(newShift);
            return;
        }

        int iNearestShift = findNearestShift(shifts, newShift);

        if (isOverlappingShift(shifts.get(iNearestShift), newShift) ||
            (iNearestShift + 1 < shifts.size() && 
                isOverlappingShift(shifts.get(iNearestShift + 1), newShift))
        ) {
            throw new IllegalArgumentException("Overlapping shift start at " + newShift.getStartTime());
        }

        schedule.insertShift(iNearestShift + 1, newShift);
    }

    public void update(Schedule schedule, Shift updatingShift, ShiftRequest shiftRequest){
        validateShift(updatingShift);
        updatingShift.setDoctorId(shiftRequest.doctorId());

        List<Shift> shifts = schedule.getShifts();
        int iUpdatingShift = shifts.indexOf(updatingShift);

        updatingShift.setStartTime(shiftRequest.startTime());
        updatingShift.setEndTime(shiftRequest.endTime());

        if ((iUpdatingShift - 1 >= 0 && 
                isOverlappingShift(shifts.get(iUpdatingShift - 1), updatingShift)) ||
            (iUpdatingShift + 1 < shifts.size() && 
                isOverlappingShift(shifts.get(iUpdatingShift + 1), updatingShift))
        ) {
            throw new IllegalArgumentException("Overlapping shift start at " + updatingShift.getStartTime());
        }
    }

    public PageResponse<ShiftResponse> findAll(Pageable pageable) {
        Page<Shift> pageResult = shiftRepository.findAll(pageable);
        return PageResponse.fromPage(
            pageResult, 
            pageResult.getContent().stream()
                .map(shiftMapper::convertShiftResponseFrom)
                .toList()
        );
    }

    private int findNearestShift(List<Shift> shifts, Shift targetShift){
        int iLeft = 0;
        int iRight = shifts.size()-1;
        int lowerBound = -1;
        
        while (iLeft <= iRight) {
            int iMid = (iRight - iLeft)/2 + iLeft;

            Shift currentShift = shifts.get(iMid);

            if (isOverlappingShift(currentShift, targetShift)) {
                throw new IllegalArgumentException("Overlapping shift start at " + targetShift.getStartTime());
            }

            if (currentShift.getStartTime().isBefore(targetShift.getStartTime())) {
                lowerBound = iMid;
                iLeft = iMid + 1;
            } else if (currentShift.getStartTime().isAfter(targetShift.getStartTime())) {
                iRight = iMid - 1;
            } 
        }

        return lowerBound;
    }

    private boolean isOverlappingShift(Shift shift1, Shift shift2){
        return !(shift1.getStartTime().isAfter(shift2.getEndTime())
            || shift1.getEndTime().isBefore(shift1.getStartTime()));
    }

    public void validateShift(Shift shift){
        if (shift.getStartTime().isAfter(shift.getEndTime())) {
            throw new IllegalArgumentException("shift end time before start time");
        }

        doctorClient.findById(shift.getDoctorId());
    }

    public void validateShifts(List<ShiftRequest> shifts){
        List<Long> doctorIds = new ArrayList<>();
        for (ShiftRequest shift : shifts) {
            if (shift.startTime().isAfter(shift.endTime())) {
                throw new IllegalArgumentException("shift end time before start time");
            }
            doctorIds.add(shift.doctorId());
        }
        List<Long> foundDoctorIds = doctorClient.findAllById(doctorIds)
            .stream().map(DoctorResponse::id).toList();

        if (!foundDoctorIds.containsAll(doctorIds)) {
            throw new IllegalArgumentException("Some doctor ids not found");
        }
    }

    public List<ShiftResponse> findAllBySchedule(Schedule schedule) {
        List<Long> doctorIds = new ArrayList<>();
        List<Shift> shifts = shiftRepository.findAllBySchedule(schedule);
        List<DoctorResponse> doctors = doctorClient.findAllById(doctorIds);

        return shifts.stream().map(shift->
            ShiftResponse.builder()
                .id(shift.getId())
                .doctor(doctors.stream()
                    .filter(doctor->doctor.id() == shift.getDoctorId())
                    .findFirst()
                    .orElseThrow(()-> new EntityNotFoundException("not found doctor with id: " +
                        shift.getDoctorId())))
                .endTime(shift.getEndTime())
                .startTime(shift.getStartTime())
                .build()
        ).toList();
    }
    
}
