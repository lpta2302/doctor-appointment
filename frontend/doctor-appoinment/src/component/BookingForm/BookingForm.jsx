import AppointmentForm from "../AppointmentForm/AppointmentForm";
import PersonalInfoForm from "../PersonalInfoForm/PersonalInfoForm";
import "./BookingForm.css";
import { useState } from "react";

export default function BookingForm() {
    const [step, setStep] = useState(1);
    const [formData, setFormData] = useState({
        form: {
            name: "",
            phone: "",
            email: "",
            location: "",
            description: "",
            date: "",
            department: "",
            doctor: ""
        }
    });
    const [showForm, setShowForm] = useState(true);

    const handleChange = (e) => {
        const data = { ...formData.form };
        data[e.target.name] = e.target.value;
        setFormData({
            form: data
        });
    }

    const goNext = () => {
        setStep(step + 1);
    }

    const goBack = () => {
        setStep(step - 1);
    }

    console.log(formData);

    return (
        <div className="container booking-form">
            {step === 1 && (<PersonalInfoForm handleChange={handleChange} formData={formData} goNext={goNext}/>)}

            {step === 2 && (<AppointmentForm handleChange={handleChange} formData={formData} goNext={goNext}/>)}

            {step === 3 && <h1>Thanh toán mẹ m đi!!!</h1>}
        </div>
    );
}


/*

N   A
G  OI
U H P
YN  H
E   U

N  H  U
G NO H
UE AP
Y  I

N E A U
GYNOIH
U H P

13

4 - 3 - 1

5 - 2 - 3

3 - 4 - 1


*/