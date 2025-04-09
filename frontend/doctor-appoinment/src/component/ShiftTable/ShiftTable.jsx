import { useState, useEffect } from "react";

const ShiftTable = ({ schedules, error }) => {
    const [showShiftForm, setShowShiftForm] = useState(false);
    const [shift, setShift] = useState({
        clinicId: schedules[0]?.clinicId || "",
        appliedDate: schedules[0]?.appliedDate || "",
        doctorId: 0,
        startTime: "12:00",
        endTime: "12:00"
    });

    useEffect(() => {
        if (schedules.length > 0) {
            setShift((prev) => ({
                ...prev,
                clinicId: schedules[0].clinicId,
                appliedDate: schedules[0].appliedDate
            }));
        }
    }, [schedules]);

    const addShift = async () => {
        try {
            const response = await fetch(`/api/v1/admin/schedules/${shift.clinicId}/${shift.appliedDate}/shifts`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    doctorId: shift.doctorId,
                    startTime: shift.startTime,
                    endTime: shift.endTime
                }),
            });
            
            if (response.ok) {
                alert("Add Shift Success");
                setShowShiftForm(false);
                setShift({
                    ...shift,
                    doctorId: 0,
                    startTime: "12:00",
                    endTime: "12:00"
                });
            } else {
                alert("Add Shift Failed");
            }
        } catch (error) {
            console.error(error);
        }
    };

    return (
        <div className="schedule-table">
            <button className="btn btn-primary mb-3" onClick={() => setShowShiftForm(true)}>
                Add New Shift
            </button>

            {showShiftForm && (
                <form className="modal-overlay" onSubmit={addShift}>
                    <div className="modal-content">
                        <button type="button" className="modal-close" onClick={() => setShowShiftForm(false)}>&times;</button>
                        <h3>Add Shift</h3>

                        <input
                            type="number"
                            className="form-control mb-2"
                            placeholder="Doctor Id"
                            value={shift.doctorId}
                            onChange={(e) => setShift({ ...shift, doctorId: Number(e.target.value) })}
                        />
                        <input
                            type="time"
                            className="form-control mb-2"
                            value={shift.startTime}
                            onChange={(e) => setShift({ ...shift, startTime: e.target.value })}
                        />
                        <input
                            type="time"
                            className="form-control mb-2"
                            value={shift.endTime}
                            onChange={(e) => setShift({ ...shift, endTime: e.target.value })}
                        />

                        <button type="submit" className="btn btn-success">Submit</button>
                    </div>
                </form>
            )}

            <table className="table table-striped">
                <thead className="field">
                    <tr>
                        <th scope="col">Shift Id</th>
                        <th scope="col">Doctor Id</th>
                        <th scope="col">Doctor Code</th>
                        <th scope="col">Doctor Name</th>
                        <th scope="col">Phone</th>
                        <th scope="col">Time</th>
                    </tr>
                </thead>
                <tbody className="data">
                    {schedules.length > 0 ? (
                        schedules.flatMap((schedule) =>
                            schedule.shifts.map((shift) => (
                                <tr key={shift.id}>
                                    <td>{shift.id}</td>
                                    <td>{shift.doctor.id}</td>
                                    <td>{shift.doctor.code}</td>
                                    <td>{shift.doctor.fullname}</td>
                                    <td>{shift.doctor.phoneNumber}</td>
                                    <td>{shift.startTime} - {shift.endTime}</td>
                                </tr>
                            ))
                        )
                    ) : (
                        <tr>
                            <td colSpan="6" className="text-center">No Data</td>
                        </tr>
                    )}
                </tbody>
            </table>
        </div>
    );
};

export default ShiftTable;
