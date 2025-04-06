import AppointmentForm from "../AppointmentForm/AppointmentForm";
import PersonalInfoForm from "../PersonalInfoForm/PersonalInfoForm";
import "./BookingForm.css";
import { useState } from "react";

export default function BookingForm() {
    const [step, setStep] = useState(1);
    const [formData, setFormData] = useState({
        form: {
            appointmentDate: "",
            appointmentTime: "12:00",
            clinicId: null,
            isOldPatient: false,
            patient: {}  // Chứa thông tin patient sẽ được cập nhật sau
        }
    });

    // Khởi tạo patient state
    const [patient, setPatient] = useState({
        gender: "MALE",
        fullname: "",
        address: "",
        phoneNumber: "",
        dateOfBirth: ""
    });

    // Hàm cập nhật thông tin bệnh nhân
    const handlePatientChange = (e) => {
        const { name, value } = e.target;
        setPatient((prevPatient) => ({
            ...prevPatient,
            [name]: value, // Cập nhật đúng theo name (gender, fullname, phoneNumber, ...)
        }));
    };

    // Hàm đi qua các bước và lưu thông tin patient vào formData
    const goNext = () => {
        if (step === 1) {
            const { fullname, phoneNumber, address, gender, dateOfBirth } = patient;
            if (!fullname || !phoneNumber || !address || !gender || !dateOfBirth) {
                alert("Vui lòng nhập đầy đủ thông tin cá nhân.");
                return;
            }

            // Lưu thông tin patient vào formData
            setFormData((prevFormData) => ({
                form: {
                    ...prevFormData.form,
                    patient: patient  // Lưu thông tin patient vào formData
                }
            }));
        }
        setStep(step + 1); // Chuyển sang bước tiếp theo
    };

    // Hàm cập nhật các thông tin còn lại trong formData
    const handleAppointmentChange = (e) => {
        const { name, value } = e.target;

        // Chuyển giá trị clinicId thành số
        if (name === 'clinicId') {
            setFormData((prevFormData) => ({
                form: {
                    ...prevFormData.form,
                    [name]: value ? Number(value) : null,  // Chuyển giá trị clinicId thành số
                }
            }));
        } else {
            setFormData((prevFormData) => ({
                form: {
                    ...prevFormData.form,
                    [name]: value,
                }
            }));
        }
    };


    console.log(formData);

    return (
        <div className="container booking-form">
            {step === 1 && (
                <PersonalInfoForm
                    handlePatientChange={handlePatientChange}
                    patient={patient}  // Truyền state patient xuống form
                    goNext={goNext}
                />
            )}

            {step === 2 && (
                <AppointmentForm
                    formData={formData} // Truyền formData xuống AppointmentForm
                    handleAppointmentChange={handleAppointmentChange} // Hàm cập nhật các giá trị của appointment
                    goNext={goNext}
                />
            )}

            {step === 3 && (
                <h1>Đăng ký thành công !!!</h1>
            )}
        </div>
    );
}
