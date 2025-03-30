import React, { useState } from "react";
import { Form, Button, Container, Row, Col } from "react-bootstrap";
import TagRoom from "../../component-router/BookAppointment/TagRoom/TagRoom.jsx";
import "./LookingInformation.css";
import Footer from "../../Footer/Footer.jsx";

const LookingInformation = () => {
  const [cccd, setCccd] = useState("");
  const [showResult, setShowResult] = useState(false);

  const handleSearch = () => {
    if (cccd.trim() !== "") {
      setShowResult(true);
    }
  };

  return (
    <div className="page-container">
      <Container className="looking-container">
        <Row className="align-items-center search-box">
          <Col xs={8}>
            <Form.Control
              type="text"
              placeholder="Nhập số CCCD"
              value={cccd}
              onChange={(e) => setCccd(e.target.value)}
            />
          </Col>
          <Col xs={4}>
            <Button className="search-btn" onClick={handleSearch}>
              Tìm kiếm
            </Button>
          </Col>
        </Row>

        {showResult && (
          <div className="result-container">
            <TagRoom
              roomName="Phòng Nội Tổng Quát"
              examTime="Thứ 2 - 15/04/2025, 08:00 - 10:30"
              doctorName="TS.BS Nguyễn Văn A"
              location="Lầu 3, Phòng 302"
            />
            <Button className="print-btn">In hóa đơn</Button>
          </div>
        )}
      </Container>

      <Footer />
    </div>
  );
};

export default LookingInformation;
