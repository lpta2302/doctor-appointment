import React, { useEffect, useState } from "react";

const ScheduleTable = ({ schedules, error }) => {
    return (
        <div className="schedule-table">
            <table className="table table-striped">
                <thead>
                    <tr>
                        <th>Clinic Name</th>
                        <th>Specialization Name</th>
                    </tr>
                </thead>
                <tbody>
                    {schedules.length > 0 ? (
                        schedules.map((schedule, index) => (
                            <tr key={index}>
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
