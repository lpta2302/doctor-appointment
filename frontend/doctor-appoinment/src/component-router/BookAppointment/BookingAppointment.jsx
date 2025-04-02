import React from "react";
import { Container, Row, Col, Form, Button } from "react-bootstrap";
import "./BookingAppointment.css";
import Footer from "../../Footer/Footer.jsx";
import TagRoom from "../../component-router/BookAppointment/TagRoom/TagRoom.jsx";

const BookingAppointment = () => {
  return (
    <Container fluid className="booking-container">
      <Row className="justify-content-center align-items-start flex-grow-1">
        <Col xs={12} md={10} lg={8} className="booking-box p-4">
          <Row>
            {/* Phần nhập thông tin bệnh nhân */}
            <Col xs={12} md={6} className="booking-form">
              <h2>Thông tin bệnh nhân</h2>
              <Form>
                <Form.Group className="mb-3">
                  <Form.Label>Họ và tên</Form.Label>
                  <Form.Control type="text" placeholder="Nhập họ và tên" />
                </Form.Group>

                <Form.Group className="mb-3">
                  <Form.Label>Năm sinh</Form.Label>
                  <Form.Control type="number" placeholder="Nhập năm sinh" />
                </Form.Group>

                <Form.Group className="mb-3">
                  <Form.Label>Số CCCD</Form.Label>
                  <Form.Control type="text" placeholder="Nhập số CCCD" />
                </Form.Group>

                <Form.Group className="mb-3">
                  <Form.Label>Chọn phòng khám</Form.Label>
                  <Form.Select>
                    <option value="">-- Chọn phòng khám --</option>
                    <option value="P101">Phòng Nội (P101)</option>
                    <option value="P102">Phòng Nhi (P102)</option>
                    <option value="P103">Phòng Tai Mũi Họng (P103)</option>
                  </Form.Select>
                </Form.Group>

                {/* Button đặt lịch */}
                <Button variant="primary" className="w-100 mt-3">
                  Đặt lịch hẹn
                </Button>
              </Form>
            </Col>

            {/* Phần hiển thị thông tin phòng khám */}
            <Col xs={12} md={6} className="booking-info d-flex align-items-center justify-content-center">
              <TagRoom 
                roomName="Phòng Nội Tổng Quát" 
                examTime="Thứ 2 - 15/04/2025, 08:00 - 10:30" 
                doctorName="TS.BS Nguyễn Văn A" 
                location="Lầu 3, Phòng 302" 
              />
            </Col>
          </Row>
        </Col>
      </Row>
      <Footer />
    </Container>
  );
};

export default BookingAppointment;
