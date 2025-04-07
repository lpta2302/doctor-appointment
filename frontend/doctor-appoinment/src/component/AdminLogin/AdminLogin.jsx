import React from "react";
import "./AdminLogin.css"; // nhớ import css
import logo from "../../assets/image/logo_bookingapp.png";

const AdminLogin = () => {
  return (
    <div className="container d-flex justify-content-center align-items-center login-container">
      <img src={logo} alt="Logo" />
      <h1>CHÀO MỪNG ĐẾN TRANG ADMIN !</h1>
    </div>
  );
};

export default AdminLogin;
