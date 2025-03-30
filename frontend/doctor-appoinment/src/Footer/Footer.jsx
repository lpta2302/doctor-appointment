import React from "react";
import { Container, Row, Col } from "react-bootstrap";
import { FiLogOut } from "react-icons/fi"; 
import "./Footer.css";

const Footer = () => {
  return (
    <footer className="footer">
      <Container fluid>
        <Row className="align-items-center">
          {/* Đăng xuất */}
          <Col xs="6" className="d-flex align-items-center">
            <FiLogOut className="logout-icon" />
            <span className="logout-text">ĐĂNG XUẤT</span>
          </Col>

          {/* Copyright - Clinic */}
          <Col xs="6" className="text-end">
            <a href="https://www.ueh.edu.vn/" className="copyright-btn">
              © CLINIC
            </a>
          </Col>
        </Row>
      </Container>
    </footer>
  );
};

export default Footer;
