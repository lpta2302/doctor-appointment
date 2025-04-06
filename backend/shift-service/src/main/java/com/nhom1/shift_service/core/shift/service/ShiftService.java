package com.nhom1.shift_service.core.shift.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nhom1.shift_service.core.doctor.client.DoctorClient;
import com.nhom1.shift_service.core.doctor.dto.DoctorResponse;
import com.nhom1.shift_service.core.schedule.entity.Schedule;
import com.nhom1.shift_service.core.shift.dto.ShiftRequest;
import com.nhom1.shift_service.core.shift.dto.ShiftResponse;
import com.nhom1.shift_service.core.shift.entity.Shift;
import com.nhom1.shift_service.core.shift.mapper.ShiftMapper;
import com.nhom1.shift_service.core.shift.repository.ShiftRepository;
import com.nhom1.shift_service.kafka.shift.ShiftProducer;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShiftService {
    private final ShiftProducer shiftProducer;
    private final ShiftRepository shiftRepository;
    private final ShiftMapper shiftMapper;
    private final DoctorClient doctorClient;

    public List<Shift> createShifts(List<ShiftRequest> shiftRequests) {
        validateShifts(shiftRequests);
        Map<Long, DoctorResponse> doctors = new HashMap<>();
        
        doctorClient.findAllById(shiftRequests
            .stream()
            .map(s->s.doctorId())
            .collect(Collectors.toSet()))
            .forEach(d->
                doctors.put(d.id(), d));

        final NavigableSet<Shift> newShifts = new TreeSet<>(
            (shift1, s2) -> shift1.getStartTime().compareTo(s2.getStartTime())
        );

        shiftRequests.forEach(shiftRequest->{
            DoctorResponse doctor = doctors.get(shiftRequest.doctorId());

            Shift newShift = shiftMapper.convertShiftFrom(shiftRequest, doctor);

            
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

    public Shift addShift(Schedule schedule, ShiftRequest shiftRequest) {
        validateShift(shiftRequest);
        DoctorResponse doctorResponse = doctorClient.findById(shiftRequest.doctorId())
            .orElseThrow(()->new EntityNotFoundException("not found doctor with ID: "+shiftRequest.doctorId()));

        Shift newShift = shiftMapper.convertShiftFrom(shiftRequest, doctorResponse);

        List<Shift> shifts = schedule.getShifts();
        if (shifts == null || shifts.isEmpty()) {
            schedule.addShift(newShift);
            return newShift;
        }

        int iNearestShift = findNearestShift(shifts, newShift);

        if (isOverlappingShift(shifts.get(iNearestShift), newShift) ||
            ((iNearestShift + 1 < shifts.size() && 
                isOverlappingShift(shifts.get(iNearestShift + 1), newShift)))
        ) {
            throw new IllegalArgumentException("Overlapping shift start at " + newShift.getStartTime());
        }

        schedule.insertShift(iNearestShift + 1, newShift);
        
        return newShift;
    }

    public void update(Schedule schedule, Shift updatingShift, ShiftRequest shiftRequest){
        validateShift(shiftRequest);

        if (!shiftRequest.doctorId().equals(updatingShift.getDoctorId())) {
            DoctorResponse doctorResponse = 
                doctorClient.findById(shiftRequest.doctorId())
                .orElseThrow(()-> new EntityNotFoundException(
                    "not found doctor with id: "  + shiftRequest.doctorId()
                ));
            updatingShift.setDoctorId(shiftRequest.doctorId());
            updatingShift.setDoctorCode(doctorResponse.code());
            updatingShift.setDoctorFullname(doctorResponse.fullname());
            updatingShift.setDoctorPhoneNumber(doctorResponse.phoneNumber());
        }

        if (updatingShift.getStartTime().equals(shiftRequest.startTime())
        && updatingShift.getEndTime().equals(shiftRequest.endTime())) {
            return;
        }
        

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

        shiftProducer.sendUpdatedShiftMessage(shiftMapper.convertShiftInfoFrom(updatingShift));
    }

    public List<ShiftResponse> findAllBySchedule(Schedule schedule) {
        List<Shift> shifts = shiftRepository.findAllBySchedule(schedule);
        
        Set<Long> doctorIds = new HashSet<>();
        shifts.stream().map(shift->shift.getDoctorId()).forEach(
            doctorId->doctorIds.add(doctorId)
        );
        List<DoctorResponse> doctors = doctorClient.findAllById(doctorIds);

        return shifts.stream().map(shift->
            ShiftResponse.builder()
                .id(shift.getId())
                .doctor(doctors.stream()
                    .filter(doctor->doctor.id().equals(shift.getDoctorId()))
                    .findFirst()
                    .orElseThrow(()-> new EntityNotFoundException("not found doctor with id: " +
                        shift.getDoctorId())))
                .endTime(shift.getEndTime())
                .startTime(shift.getStartTime())
                .build()
        ).toList();
    }
    
    public List<ShiftResponse> findAllBySchedules(List<Schedule> schedules) {
        List<Shift> shifts = shiftRepository.findAllBySchedules(schedules);
        
        Set<Long> doctorIds = new HashSet<>();
        shifts.stream().map(shift->shift.getDoctorId()).forEach(
            doctorId->doctorIds.add(doctorId)
        );
        List<DoctorResponse> doctors = doctorClient.findAllById(doctorIds);

        return shifts.stream().map(shift->
            ShiftResponse.builder()
                .id(shift.getId())
                .doctor(doctors.stream()
                    .filter(doctor->doctor.id().equals(shift.getDoctorId()))
                    .findFirst()
                    .orElseThrow(()-> new EntityNotFoundException("not found doctor with id: " +
                        shift.getDoctorId())))
                .endTime(shift.getEndTime())
                .startTime(shift.getStartTime())
                .build()
        ).toList();
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

    public void removeShift(Shift removingShift) {
        shiftProducer.sendDeletedShiftMessage(shiftMapper.convertShiftInfoFrom(removingShift));
    }

    private boolean isOverlappingShift(Shift shift1, Shift shift2){
        return shift1.getStartTime().isBefore(shift2.getEndTime()) &&
            shift1.getEndTime().isAfter(shift2.getStartTime());
    }

    public void validateShift(ShiftRequest shift){
        if (shift.startTime().isAfter(shift.endTime())) {
            throw new IllegalArgumentException("shift end time before start time");
        }
    }

    public void validateShifts(List<ShiftRequest> shifts){
        for (ShiftRequest shift : shifts) {
            validateShift(shift);
        }
    }

    public List<ShiftResponse> convertToShiftResponses(List<Shift> shifts) {
        Set<Long> doctorIds = new HashSet<>();
        shifts.stream().map(shift->shift.getDoctorId()).forEach(
            doctorId->doctorIds.add(doctorId)
        );
        List<DoctorResponse> doctors = doctorClient.findAllById(doctorIds);

        return shifts.stream().map(shift->
            ShiftResponse.builder()
                .id(shift.getId())
                .doctor(doctors.stream()
                    .filter(doctor->doctor.id().equals(shift.getDoctorId()))
                    .findFirst()
                    .orElseThrow(()-> new EntityNotFoundException("not found doctor with id: " +
                        shift.getDoctorId())))
                .endTime(shift.getEndTime())
                .startTime(shift.getStartTime())
                .build()
        ).toList();
    }
}
