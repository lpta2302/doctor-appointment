import React from "react";
import "./Wellcome.css";
import logo from "../../img/logo_bookingapp.png";

const Wellcome = () => {
  return (
    <div className="admin-dashboard">
      <div className="dashboard-content">
        <img src={logo} alt="Clinic Logo" className="dashboard-logo" />
        
        <div className="dashboard-text">
          <h1 className="dashboard-title">TRANG QUẢN TRỊ PHÒNG KHÁM</h1>
          <h1 className="dashboard-subtitle">CLINIC ABC</h1>
        </div>
      </div>
    </div>
  );
};

export default Wellcome;
