const ShiftTable = ({ schedules, error }) => {
    console.log("ShiftTable", schedules);
    
    return (
        <div className="schedule-table">
            <button className="btn btn-primary mb-3">
                Add New Shift
            </button>

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
