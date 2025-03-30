import React from "react";
import "./DoctorTag.css";

const DoctorTag = ({ doctor, onSelect, isSelected }) => {
  return (
    <div className={`doctor-tag ${isSelected ? "selected" : ""}`} onClick={onSelect}>
      <div className="doctor-info">
        <p><strong>Bác sĩ:</strong> {doctor.name}</p>
        <p><strong>Năm sinh:</strong> {doctor.birthYear}</p>
      </div>
      <p className="doctor-specialty"><strong>Chuyên khoa:</strong> {doctor.specialty}</p>
    </div>
  );
};

export default DoctorTag;
