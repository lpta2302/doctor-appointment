import React from "react";
import { Container, Form, Button, Card } from "react-bootstrap";
import "./LogIn.css";
import logo from "../img/logo_bookingapp.png";

const LogIn = () => {
  return (
    <Container className="login-container">
      <Card className="login-card">
        {/* Logo */}
        <div className="login-logo-wrapper">
          <img src={logo} alt="Logo" className="login-logo" />
        </div>

        {/* Tiêu đề */}
        <h2 className="login-title">CLINIC DASHBOARD</h2>

        {/* Form đăng nhập */}
        <Form className="login-form">
          <Form.Group className="mb-3">
            <Form.Control type="text" placeholder="Mời nhập mã nhân viên" />
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Control type="password" placeholder="Mời nhập mật khẩu" />
          </Form.Group>

          <Button variant="primary" className="login-button" block>
            Đăng nhập
          </Button>
        </Form>
      </Card>
    </Container>
  );
};

export default LogIn;
