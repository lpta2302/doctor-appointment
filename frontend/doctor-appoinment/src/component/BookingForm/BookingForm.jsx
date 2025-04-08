import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import AppointmentForm from "../AppointmentForm/AppointmentForm";
import PersonalInfoForm from "../PersonalInfoForm/PersonalInfoForm";
import "./BookingForm.css";

export default function BookingForm() {
    const [step, setStep] = useState(1);
    const [formData, setFormData] = useState({
        form: {
            appointmentDate: "",
            appointmentTime: "12:00",
            clinicId: null,
            isOldPatient: false,
            patient: {}
        }
    });

    const [patient, setPatient] = useState({
        gender: "MALE",
        fullname: "",
        address: "",
        phoneNumber: "",
        dateOfBirth: ""
    });
    const navigate = useNavigate();
    const [countdown, setCountdown] = useState(5);

    useEffect(() => {
        if (step === 3) {
            const interval = setInterval(() => {
                setCountdown((prev) => prev - 1);
            }, 1000);

            if (countdown === 0) {
                clearInterval(interval);
                navigate("/");
            }

            return () => clearInterval(interval);
        }
    }, [step, countdown, navigate]);

    const handlePatientChange = (e) => {
        const { name, value } = e.target;
        setPatient((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    const handleOldPatientChange = (e) => {
        const isChecked = e.target.checked;
        setFormData((prev) => ({
            form: {
                ...prev.form,
                isOldPatient: isChecked,
            }
        }));
    };

    const goNext = () => {
        if (step === 1) {
            const { fullname, phoneNumber, address, gender, dateOfBirth } = patient;
            if (!fullname || !phoneNumber || !address || !gender || !dateOfBirth) {
                alert("Vui lòng nhập đầy đủ thông tin cá nhân.");
                return;
            }

            setFormData((prev) => ({
                form: {
                    ...prev.form,
                    patient: patient,
                }
            }));
        }
        setStep(step + 1);
    };

    const goBack = () => setStep(step - 1);

    const handleAppointmentChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
            form: {
                ...prev.form,
                [name]: name === 'clinicId' ? Number(value) : value,
            }
        }));
    };

    // useEffect(() => {
    //     if (step === 3) {
    //         const timer = setTimeout(() => {
    //             window.location.href = "/";
    //         }, 5000);

    //         return () => clearTimeout(timer);
    //     }
    // }, [step]);

    return (
        <div className="container booking-form">
            {step === 1 && (
                <PersonalInfoForm
                    handlePatientChange={handlePatientChange}
                    patient={patient}
                    goNext={goNext}
                    isOldPatient={formData.form.isOldPatient}
                    handleOldPatientChange={handleOldPatientChange}
                />
            )}

            {step === 2 && (
                <AppointmentForm
                    formData={formData}
                    handleAppointmentChange={handleAppointmentChange}
                    goNext={goNext}
                    goBack={goBack}
                />
            )}

            {step === 3 && (
                <div className="popup-success">
                    <h1>Đăng ký thành công !!!</h1>
                    <p>Sẽ quay về trang chủ sau {countdown} giây...</p>
                    <button type="button" className="btn-submit m-3" onClick={() => { navigate("/"); }}>
                        Back
                    </button>
                </div>
            )}
        </div>
    );
}
