const PersonalInfoForm = ({ handlePatientChange, patient, goNext, isOldPatient, handleOldPatientChange }) => {
    return (
        <form action="" className="row">
            <div className="col-lg-6">
                <div className="my-3 mx-2">
                    <label className="form-label">Fullname:</label>
                    <input
                        type="text"
                        className="form-control"
                        placeholder="Enter fullname"
                        name="fullname"
                        onChange={handlePatientChange}
                        value={patient.fullname}
                        required
                    />
                </div>

                <div className="my-3 mx-2">
                    <label className="form-label">Phone Number:</label>
                    <input
                        type="text"
                        className="form-control"
                        placeholder="Enter phone number"
                        name="phoneNumber"
                        onChange={handlePatientChange}
                        value={patient.phoneNumber}
                        required
                    />
                </div>

                <div className="my-3 mx-2">
                    <label className="form-label">Address:</label>
                    <input
                        type="text"
                        className="form-control"
                        placeholder="Enter address"
                        name="address"
                        onChange={handlePatientChange}
                        value={patient.address}
                        required
                    />
                </div>

                <div className="my-3 mx-2">
                    <label className="form-label">Gender:</label>
                    <select
                        className="form-control"
                        name="gender"
                        onChange={handlePatientChange}
                        value={patient.gender}
                        required
                    >
                        <option value="MALE">Male</option>
                        <option value="FEMALE">Female</option>
                    </select>
                </div>

                <div className="my-3 mx-2">
                    <label className="form-label">Date Of Birth:</label>
                    <input
                        type="date"
                        className="form-control"
                        name="dateOfBirth"
                        onChange={handlePatientChange}
                        value={patient.dateOfBirth}
                        required
                    />
                </div>
            </div>

            <div className="col-lg-6">
                <div className="my-3 mx-2">
                    <label htmlFor="description" className="form-label">
                        Description:
                    </label>
                    <textarea
                        type="text"
                        className="form-control desc-input"
                        id="description"
                        placeholder="Enter description"
                        name="description"
                    />
                </div>
            </div>

            <div className="my-1 mx-2">
                <label className="form-label"></label>
                <input
                    type="checkbox"
                    checked={isOldPatient}
                    onChange={handleOldPatientChange}
                />{" "}
                Tôi đã từng sử dụng dịch vụ
            </div>

            <button type="button" className="btn-submit m-3" onClick={goNext}>
                Next
            </button>
        </form>
    );
};

export default PersonalInfoForm;
