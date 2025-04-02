import React from "react";
import { Row, Col } from "react-bootstrap";
import "./TagRoom.css";

const TagRoom = ({ roomName, examTime, doctorName, location }) => {
  return (
    <div className="tagroom-container">
      <Row className="align-items-center">
        {/* Bên trái: Thông tin phòng khám */}
        <Col md={8} className="tagroom-info">
          <h3 className="room-name">{roomName}</h3>
          <p className="exam-time">🕒 {examTime}</p>
          <p className="doctor-name">👨‍⚕️ {doctorName}</p>
          <p className="doctor-name">👨 Bệnh nhân: Họ tên bệnh nhân - Năm sinh</p>
        </Col>

        {/* Bên phải: Địa điểm phòng */}
        <Col md={4} className="tagroom-location text-md-end text-center">
          <p>📍 <span className="location">{location}</span></p>
        </Col>
      </Row>
    </div>
  );
};

export default TagRoom;