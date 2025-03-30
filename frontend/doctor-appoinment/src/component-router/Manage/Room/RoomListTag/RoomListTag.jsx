import React from "react";
import "./RoomListTag.css";

const RoomListTag = ({ clinic, onSelect, isSelected }) => {
  return (
    <div className={`clinic-tag ${isSelected ? "selected" : ""}`} onClick={onSelect}>
      <div className="clinic-info">
        <p><strong>Tên phòng khám:</strong> {clinic.name}</p>
        <p><strong>Địa chỉ:</strong> {clinic.address}</p>
        <p className="clinic-specialty"><strong>Chuyên khoa:</strong> {clinic.specialty}</p>
      </div>
    </div>
  );
};

export default RoomListTag;
