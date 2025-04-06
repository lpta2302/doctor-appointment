import React, { useState } from "react";

const ScheduleTable = ({ schedules, error }) => {
    const [showScheduleForm, setShowScheduleForm] = useState(false);
    const [schedule, setSchedule] = useState({
        clinicId: "",
        appliedDate: "",
    });

    const addSchedule = async () => {
        try {
            const response = await fetch("/api/v1/admin/schedules", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    clinicId: Number(schedule.clinicId),
                    appliedDate: schedule.appliedDate,
                    shiftRequests: []
                }),
            });

            if (response.ok) {
                alert("Add Schedule Success");
                setShowScheduleForm(false);
                setSchedule({ clinicId: "", appliedDate: "" });
            } else {
                alert("Add Schedule Failed");
            }
        } catch (error) {
            console.error(error);
        }
    };

    return (
        <div className="schedule-table">
            <button className="btn btn-primary mb-3" onClick={() => setShowScheduleForm(true)}>
                Add New Schedule
            </button>

            {showScheduleForm && (
                <div className="modal-overlay" onClick={() => setShowScheduleForm(false)}>
                    <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                        <button className="modal-close" onClick={() => setShowScheduleForm(false)}>&times;</button>
                        <h3>Add Schedule</h3>
                        <input
                            type="number"
                            className="form-control mb-2"
                            placeholder="Clinic Id"
                            value={schedule.clinicId}
                            onChange={(e) => setSchedule({ ...schedule, clinicId: e.target.value })}
                        />
                        <input
                            type="date"
                            className="form-control mb-2"
                            placeholder="Applied Date"
                            value={schedule.appliedDate}
                            onChange={(e) => setSchedule({ ...schedule, appliedDate: e.target.value })}
                        />
                        <button className="btn btn-success" onClick={addSchedule}>Submit</button>
                    </div>
                </div>
            )}

            <table className="table table-striped">
                <thead>
                    <tr>
                        <th>Clinic Id</th>
                        <th>Clinic Name</th>
                        <th>Specialization Name</th>
                    </tr>
                </thead>
                <tbody>
                    {schedules.length > 0 ? (
                        schedules.map((schedule, index) => (
                            <tr key={index}>
                                <td>{schedule.clinicId}</td>
                                <td>{schedule.clinicName}</td>
                                <td>{schedule.specializationName}</td>
                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan="5" className="text-center">No Data</td>
                        </tr>
                    )}
                </tbody>
            </table>
        </div>
    );
};

export default ScheduleTable;
