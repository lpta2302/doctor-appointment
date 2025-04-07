import "./UserBookingInfoItem.css";

const UserBookingInfoItem = () => {
    return (
        <>
            <div className="user-booking-info">
                <h4 className="specialization">Tai - Mũi - Họng</h4>
                <p className="clinic">Phòng khám: Phòng khám A</p>
                <p className="date">Ngày khám: 07-04-2025</p>
                <p className="time">Giờ khám: 09:00 ~ 09:30</p>
                <p className="status pending">Trạng thái: Đang chờ</p>
                <button className="cancel-btn">Huỷ lịch</button>
            </div>
        </>
    )
}

export default UserBookingInfoItem;