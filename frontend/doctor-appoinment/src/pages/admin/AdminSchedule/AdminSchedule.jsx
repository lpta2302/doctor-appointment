import ScheduleTable from "../../../component/ScheduleTable/ScheduleTable";
import ShiftTable from "../../../component/ShiftTable/ShiftTable";
import React, { useEffect, useState } from "react";

const AdminSchedule = () => {
    const [clinicId, setClinicId] = useState("");
    const [appliedDate, setAppliedDate] = useState("");
    const [schedules, setSchedules] = useState([]);
    const [error, setError] = useState("");

    useEffect(() => {
        fetchSchedules();
    }, [clinicId, appliedDate]);

    const fetchSchedules = async () => {
        if (!clinicId || !appliedDate) {
            setError("Vui lòng nhập đủ Clinic Id và Applied Date");
            return;
        }

        try {
            const response = await fetch(`/api/v1/admin/schedules/${clinicId}/${appliedDate}?clinicId=${clinicId}&appliedDate=${appliedDate}`);
            if (!response.ok) {
                throw new Error("Failed to fetch schedules");
            }

            const data = await response.json();
            setSchedules([data]); // API trả object
            setError("");
        } catch (err) {
            console.error(err);
            setError("Không tìm thấy dữ liệu hoặc có lỗi xảy ra");
        }
    };

    return (
        <div className="container text-center">
            <div className="row">
                <div className="mb-3 d-flex gap-2">
                    <input
                        type="text"
                        className="form-control"
                        placeholder="Clinic Id"
                        value={clinicId}
                        onChange={(e) => setClinicId(e.target.value)}
                    />
                    <input
                        type="date"
                        className="form-control"
                        value={appliedDate}
                        onChange={(e) => setAppliedDate(e.target.value)}
                    />
                </div>
            </div>

            {error && <div className="alert alert-danger">{error}</div>}

            <div className="row justify-content-center">
                <div className="col-4">
                    <ScheduleTable schedules={schedules} error={error} />
                </div>

                <div className="col-7">
                    <ShiftTable schedules={schedules} error={error} />
                </div>
            </div>
        </div>
    )
}

export default AdminSchedule;