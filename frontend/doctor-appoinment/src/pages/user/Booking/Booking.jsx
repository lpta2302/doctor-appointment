import BookingForm from "../../../component/BookingForm/BookingForm";
import "./Booking.css";

export default function Booking() {
    return (
        <div className="container my-4 booking">
            <h4>Enter Your Information</h4>
            <div className="line mx-3"></div>
            <div className="row mx-5 my-4 profile">
                <div className="col-12">
                    <BookingForm />
                </div>
            </div>
        </div>
    );
}
