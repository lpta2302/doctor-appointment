import React from "react";
import { Container, Navbar, Nav, NavDropdown } from "react-bootstrap";
import { NavLink, useNavigate } from "react-router-dom";
import "./Header.css";
import logo from "../img/logo_bookingapp.png";
import avatar from "../img/avatar.png";

const Header = () => {
  const navigate = useNavigate();

  return (
    <Navbar expand="lg" className="custom-header w-100">
      <Container fluid className="d-flex align-items-center justify-content-between">
        
        {/* Logo + Text */}
        <div className="d-flex align-items-center">
          <div className="logo-wrapper">
            <img src={logo} alt="Logo" className="header-logo" />
          </div>
          <span className="clinic-text">CLINIC DASHBOARD</span>
        </div>

        {/* Nav Menu */}
        <Nav className="nav-links d-flex justify-content-center">
          <NavLink to="/dat-lich-hen" className="nav-item">ĐẶT LỊCH HẸN</NavLink>
          <NavLink to="/thong-tin" className="nav-item">TRA CỨU THÔNG TIN</NavLink>
          <NavLink to="/thong-ke" className="nav-item">THỐNG KÊ</NavLink>
          
          {/* Dropdown Chỉnh Sửa */}
          <NavDropdown title="QUẢN LÝ" id="basic-nav-dropdown">
            <NavDropdown.Item onClick={() => navigate("/quan-ly/bac-si")}>Bác sĩ</NavDropdown.Item>
            <NavDropdown.Item onClick={() => navigate("/quan-ly/phong-kham")}>Phòng khám</NavDropdown.Item>
            <NavDropdown.Item onClick={() => navigate("/quan-ly/lich-kham")}>Lịch khám</NavDropdown.Item>
          </NavDropdown>
        </Nav>

        {/* Profile */}
        <div className="d-flex align-items-center profile-info">
          <div className="text-end me-2">
            <div className="employee-name">Nguyễn Văn A</div>
            <div className="employee-id">MNV001</div>
          </div>
          <img src={avatar} alt="Avatar" className="avatar-img" />
        </div>
      </Container>
    </Navbar>
  );
};

export default Header;
