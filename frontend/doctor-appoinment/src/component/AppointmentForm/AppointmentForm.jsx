import { useState, useEffect } from "react";

const AppointmentForm = ({ formData, handleAppointmentChange, goNext }) => {
    const [specializations, setSpecializations] = useState([]);
    const [specialization, setSpecialization] = useState("");
    const [clinics, setClinics] = useState([]);
    const [clinic, setClinic] = useState("");
    const [times, setTimes] = useState([]);

    const getAllSpecializations = async () => {
        try {
            const response = await fetch(`/api/v1/specializations?page=0&size=15`, {
                method: "GET"
            })

            if (!response.ok) {
                throw new Error("Failed to fetch specializations");
            }
            const data = await response.json();
            setSpecializations(data.content);  // lưu data vào state
        } catch (error) {
            console.error('error: ', error);
        }
    }

    useEffect(() => {
        getAllSpecializations();
    }, [])

    const getAllClinics = async () => {
        if (!specialization) return;

        try {
            const response = await fetch(`/api/v1/clinics/search?page=0&size=15&code=dfgd&name=dfgd&specializationId=${specialization}`, {
                method: "GET",
            });

            if (!response.ok) {
                throw new Error("Failed to fetch clinics");
            }

            const data = await response.json();
            setClinics(data.content);
        } catch (error) {
            console.error('Error fetching clinics:', error);
        }
    };

    useEffect(() => {
        getAllClinics();
    }, [specialization])

    const getTime = async () => {
        if (!formData.form.clinicId || formData.form.appointmentDate == "") return;

        try {
            const response = await fetch(`/api/v1/appointments/${formData.form.clinicId}/${formData.form.appointmentDate}/time`, {
                method: "GET",
            });

            if (!response.ok) {
                throw new Error("Failed to fetch clinics");
            }

            const data = await response.json();
            setTimes(data);
            // console.log(data);
        } catch {
            alert("Don't have any shifts !");
        }
    }

    const addAppointment = async () => {
        const { appointmentDate, appointmentTime, clinicId, isOldPatient, patient } = formData.form;
        const bodyData = {
            appointmentDate,
            appointmentTime,
            clinicId,
            isOldPatient,
            patient: {
                gender: patient.gender,
                fullname: patient.fullname,
                address: patient.address,
                phoneNumber: patient.phoneNumber,
                dateOfBirth: patient.dateOfBirth,
            }
        };

        try {
            const response = await fetch(`/api/v1/appointments`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(bodyData)  // Dữ liệu được gửi lên server
            });

            if (!response.ok) {
                throw new Error("Failed to add appointment");
            }

            alert("Success !");
            goNext();
        } catch (error) {
            console.error("Error:", error);
            alert("Failed !");
        }
    };


    useEffect(() => {
        // console.log(formData.form.clinicId, formData.form.appointmentDate);
        getTime();
    }, [formData.form.clinicId, formData.form.appointmentDate])

    console.log(formData);

    return (
        <form action="" className="row">
            <div className="mt-3 mb-2 mx-2">
                <label htmlFor="appointmentDate" className="form-label">
                    Appointment Date:
                </label>
                <input
                    type="date"
                    className="form-control"
                    id="appointmentDate"
                    name="appointmentDate"
                    value={formData.form.appointmentDate}
                    onChange={handleAppointmentChange}
                    required
                />
            </div>

            {formData.form.appointmentDate != "" && (
                <div className="my-2 mx-2">
                    <label htmlFor="specializationId" className="form-label">
                        Department (Khoa):
                    </label>
                    <select
                        className="form-control"
                        id="specializationId"
                        name="specializationId"
                        value={specialization || ''}
                        onChange={(e) => { setSpecialization(e.target.value) }}
                        required
                    >
                        <option value="">-- Select Department --</option>
                        {specializations.map((item) => (
                            <option key={item.id} value={item.id}>
                                {item.name} - {item.price.toLocaleString()} VND
                            </option>
                        ))}
                    </select>
                </div>
            )}

            {specialization != "" && (
                <div className="my-2 mx-2">
                    <label htmlFor="clinicId" className="form-label">
                        Clinic Name:
                    </label>
                    <select
                        className="form-control"
                        id="clinicId"
                        name="clinicId"
                        value={formData.form.clinicId || ''}  // Giá trị của clinicId có thể là null hoặc số
                        onChange={handleAppointmentChange}  // Hàm sẽ xử lý thay đổi giá trị
                        required
                    >
                        <option value="">-- Select Clinic --</option>
                        {clinics.map((item) => (
                            <option key={item.id} value={item.id}>
                                {item.name}
                            </option>
                        ))}
                    </select>
                </div>
            )}

            {formData.form.clinicId && (
                <div className="my-2 mx-2">
                    <label htmlFor="appointmentTime" className="form-label">
                        Appointment Time:
                    </label>
                    <select
                        className="form-control"
                        id="appointmentTime"
                        name="appointmentTime"
                        onChange={handleAppointmentChange}
                        required
                    >
                        <option value="">-- Select Appointment Time --</option>
                        {times
                            .filter((item) => item.isAvailable)  // Lọc ra các thời gian có isAvailable là true
                            .map((item) => (
                                <option key={item.time} value={item.time.slice(0, 5)}>
                                    {item.time.slice(0, 5)}
                                </option>
                            ))}
                    </select>
                </div>
            )}



            <button type="button" className="btn-submit m-3" onClick={addAppointment}>
                Next
            </button>
        </form>
    );
};

export default AppointmentForm;
