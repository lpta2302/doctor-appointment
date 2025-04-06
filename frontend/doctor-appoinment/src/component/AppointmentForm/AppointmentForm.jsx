import { useState, useEffect } from "react"

const AppointmentForm = ({ formData, handleChange, goNext }) => {

    const [departments, setDepartments] = useState([]);

    useEffect(() => {
        if (formData.form.date) {
            fetchGetSpecializations();
        }
    }, [formData.form.date]);

    const fetchGetSpecializations = async (data) => {
        const response = await fetch(`${SERVICE_API}/requirement`, {
            method: "POST",
            header: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data),
        });

        if (response.ok) {
            const data = await response.json();

            setDepartments(data.content);
        }
    }

    const departmentList = departments.map((department) => {
        return (
            <option key={department.code} value={department.code}>
                {department.name}
            </option>
        )
    });

    return (
        <form action="" className="row">
            <div className="mt-3 mb-2 mx-2">
                <label htmlFor="date" className="form-label">
                    Date:
                </label>

                <input
                    type="date"
                    className="form-control"
                    id="date"
                    name="date"
                    onChange={handleChange}
                    // value=""
                    required
                />
            </div>

            {formData.form.date != "" && (
                <div className="my-2 mx-2">
                    <label
                        htmlFor="department"
                        className="form-label"
                    >
                        Department:
                    </label>
                    
                    <select
                        className="form-select"
                        onChange={handleChange}
                        id="department"
                        name="department"
                    >
                        <option>-Select All-</option>
                        {departmentList}
                    </select>
                </div>
            )}

            {formData.form.department && (
                <div className="mt-2 mb-3 mx-2">
                    <label htmlFor="doctor" className="form-label">
                        Doctor:
                    </label>
                    <select className="form-select" onChange={handleChange} id="doctor" name="doctor">
                        <option>-Select All-</option>
                        <option>Dr. John Doe</option>
                        <option>Dr. Jane Doe</option>
                        <option>Dr. Michael Doe</option>
                    </select>
                </div>
            )}

            {formData.form.doctor && (
                <button type="button" className="btn-submit m-3" onClick={goNext}>Next</button>
            )}
        </form>
    )
}

export default AppointmentForm;