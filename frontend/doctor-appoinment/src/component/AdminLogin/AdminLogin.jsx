import React from "react";
import "./AdminLogin.css";
import logo from "../../assets/image/logo_bookingapp.png";

const AdminLogin = () => {
  return (
    <div className="container d-flex justify-content-center align-items-center login-container vh-100">
      <div className="card p-4 shadow login-card" style={{ width: "350px" }}>
        {/* Logo */}
        <div className="text-center mb-3 login-logo-wrapper">
          <img src={logo} alt="Logo" className="img-fluid login-logo" style={{ maxWidth: "100px" }} />
        </div>

        {/* Tiêu đề */}
        <h2 className="text-center mb-3 login-title">CLINIC DASHBOARD</h2>

        {/* Form đăng nhập */}
        <form className="login-form">
          <div className="mb-3">
            <input type="text" className="form-control" placeholder="Mời nhập mã nhân viên" />
          </div>

          <div className="mb-3">
            <input type="password" className="form-control" placeholder="Mời nhập mật khẩu" />
          </div>

          <button type="submit" className="btn btn-primary login-button w-100">
            Đăng nhập
          </button>
        </form>
      </div>
    </div>
  );
};

export default AdminLogin;