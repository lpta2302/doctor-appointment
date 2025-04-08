import "./UserBookingInfoItem.css";

const UserBookingInfoItem = ({ appointment }) => {
    return (
        <div className="user-booking-info">
            <h4 className="specialization">{appointment.specializationName}</h4>
            <p className="clinic">Phòng khám: {appointment.clinicName}</p>
            <p className="date">Ngày khám: {appointment.appointmentDate}</p>
            <p className="time">Giờ khám: {appointment.appointmentTime}</p>
            <p className={`status ${appointment.status.toLowerCase()}`}>Trạng thái: {appointment.status}</p>
            <button className="cancel-btn">Huỷ lịch</button>
        </div>
    );
};

export default UserBookingInfoItem;
