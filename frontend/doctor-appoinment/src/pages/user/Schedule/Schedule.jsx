import { useState, useEffect } from "react";
import "./Schedule.css";
import UserBookingInfoItem from "../../../component/UserBookingInfoItem/UserBookingInfoItem";

export default function Schedule() {
    const [showSearchBooking, setShowSearchBooking] = useState(false);
    const [showViewSchedule, setShowViewSchedule] = useState(false);
    const [specializations, setSpecializations] = useState([]);
    const [scheduleData, setScheduleData] = useState([]);
    const [appliedDate, setAppliedDate] = useState("");
    const [specializationId, setSpecializationId] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");
    const [patientAppointments, setPatientAppointments] = useState([]);

    useEffect(() => {
        getAllSpecializations();
    }, []);

    const handleViewSchedule = async () => {
        setPatientAppointments([]);
        try {
            const response = await fetch(`/api/v1/schedules/${appliedDate}/specializations/${specializationId}`);
            if (!response.ok) throw new Error("Failed to get schedule !");
            const data = await response.json();
            setScheduleData(data);
            setShowViewSchedule(false);
        } catch (error) {
            console.error("Error getting schedule: ", error);
        }
    };

    const getAllSpecializations = async () => {
        try {
            const response = await fetch(`/api/v1/specializations?page=0&size=15`);
            if (!response.ok) throw new Error("Failed to get all specializations !");
            const data = await response.json();
            setSpecializations(data.content);
        } catch (error) {
            console.error("Error to get all specializations: ", error);
        }
    };

    const handleSearchBooking = async () => {
        setScheduleData([]);
        try {
            const response = await fetch(`/api/v1/patients/${phoneNumber}`);
            if (!response.ok) throw new Error("Failed to get patient appointments !");
            const data = await response.json();
            setPatientAppointments(data.appointments || []);
            setShowSearchBooking(false);
        } catch (error) {
            console.error("Error getting patient data: ", error);
        }
    };

    return (
        <div className="container user-schedule">
            <div className="row">
                <div className="col-4 text-center mt-5 content">
                    <button type="button" className="btn-submit m-3" onClick={() => setShowSearchBooking(true)}>
                        Search Booking
                    </button>

                    <button type="button" className="btn-submit m-3" onClick={() => setShowViewSchedule(true)}>
                        View Examination Schedule
                    </button>
                </div>

                {patientAppointments.length > 0 && scheduleData.length === 0 && (
                    <div className="col-12 text-center mt-3 mb-5 content">
                        <div className="row justify-content-around">
                            {patientAppointments.map((appointment, index) => (
                                <div className="col" key={index}>
                                    <UserBookingInfoItem appointment={appointment} />
                                </div>
                            ))}
                        </div>
                    </div>
                )}

                {scheduleData.length > 0 && patientAppointments.length === 0 && (
                    <div className="row mt-4">
                        {scheduleData.map((item, index) => (
                            <div className="col-12" key={index}>
                                <div className="clinic-card">
                                    <h5>Clinic: {item.clinicName}</h5>
                                    <h6>Specialization: {item.specializationName}</h6>
                                    <p>Applied Date: {item.appliedDate}</p>
                                    {item.shifts.map((shift, idx) => (
                                        <div key={idx} className="shift-card">
                                            <p>Doctor: {shift.doctor.fullname} - {shift.doctor.phoneNumber}</p>
                                            <p>Start: {shift.startTime}</p>
                                            <p>End: {shift.endTime}</p>
                                        </div>
                                    ))}
                                </div>
                            </div>
                        ))}
                    </div>
                )}

                {showSearchBooking && (
                    <div className="modal-overlay" onClick={() => setShowSearchBooking(false)}>
                        <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                            <button className="modal-close" onClick={() => setShowSearchBooking(false)}>&times;</button>
                            <h4>Search Your Booking</h4>
                            <input
                                type="number"
                                name="phone-number"
                                className="form-control mt-3 mb-3"
                                placeholder="Phone Number"
                                value={phoneNumber}
                                onChange={(e) => setPhoneNumber(e.target.value)}
                            />
                            <button className="btn btn-success" onClick={handleSearchBooking}>Submit</button>
                        </div>
                    </div>
                )}

                {showViewSchedule && (
                    <div className="modal-overlay" onClick={() => setShowViewSchedule(false)}>
                        <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                            <button className="modal-close" onClick={() => setShowViewSchedule(false)}>&times;</button>
                            <h4>Fill Information</h4>
                            <input
                                type="date"
                                name="applied-date"
                                className="form-control mt-3 mb-3"
                                value={appliedDate}
                                onChange={(e) => setAppliedDate(e.target.value)}
                            />

                            <select
                                className="form-control mb-3"
                                name="specializationId"
                                value={specializationId}
                                onChange={(e) => setSpecializationId(e.target.value)}
                            >
                                <option value="">--Select Specialization--</option>
                                {specializations.map((item) => (
                                    <option key={item.id} value={item.id}>{item.name}</option>
                                ))}
                            </select>

                            <button className="btn btn-success" onClick={handleViewSchedule}>Submit</button>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
}
