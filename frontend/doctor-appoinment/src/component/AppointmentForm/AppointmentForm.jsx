const AppointmentForm = ({formData, handleChange, goNext}) => {
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
                    <label htmlFor="department" className="form-label">
                        Department:
                    </label>
                    <select className="form-select" onChange={handleChange} id="department" name="department">
                        <option>-Select All-</option>
                        <option>Cardiology</option>
                        <option>Neurology</option>
                        <option>Orthopedic</option>
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
                <button type="submit" className="btn-submit m-3" onClick={goNext}>Next</button>
            )}
        </form>
    )
}

export default AppointmentForm;