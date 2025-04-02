import React, { useState } from "react";
import "./Room.css";
import RoomListTag from "../Room/RoomListTag/RoomListTag.jsx";
import Footer from "../../../Footer/Footer.jsx";

// Fake data
const initialClinics = [
  { name: "Phòng khám Đa khoa An Bình", address: "123 Đường Lê Lợi, Quận 1, TP.HCM", specialty: "Đa khoa" },
  { name: "Phòng khám Nhi Đồng Hạnh Phúc", address: "45 Đường Trần Hưng Đạo, Quận 5, TP.HCM", specialty: "Nhi khoa" },
  { name: "Phòng khám Nội Tiết Minh Tâm", address: "67 Đường Nguyễn Văn Trỗi, Quận Phú Nhuận, TP.HCM", specialty: "Nội tiết" },
  { name: "Phòng khám Tim Mạch Tâm An", address: "89 Đường Hai Bà Trưng, Quận 3, TP.HCM", specialty: "Tim mạch" },
  { name: "Phòng khám Da Liễu Hoàn Mỹ", address: "22 Đường Võ Thị Sáu, Quận 10, TP.HCM", specialty: "Da liễu" },
];

const Room = () => {
  const [clinics, setClinics] = useState(initialClinics);
  const [form, setForm] = useState({ name: "", address: "", specialty: "" });
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedIndex, setSelectedIndex] = useState(null);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
  };

  const handleSearch = (e) => {
    setSearchTerm(e.target.value.toLowerCase());
  };

  const handleAdd = () => {
    if (!form.name || !form.address || !form.specialty) return;
    setClinics([...clinics, form]);
    setForm({ name: "", address: "", specialty: "" });
    setSelectedIndex(null);
  };

  const handleEdit = () => {
    if (selectedIndex === null) return;
    const updated = [...clinics];
    updated[selectedIndex] = form;
    setClinics(updated);
    setForm({ name: "", address: "", specialty: "" });
    setSelectedIndex(null);
  };

  const handleDelete = () => {
    if (selectedIndex === null) return;
    setClinics(clinics.filter((_, idx) => idx !== selectedIndex));
    setForm({ name: "", address: "", specialty: "" });
    setSelectedIndex(null);
  };

  const handleSelect = (index) => {
    setForm(clinics[index]);
    setSelectedIndex(index);
  };

  const filteredClinics = clinics.filter((clinic) =>
    clinic.name.toLowerCase().includes(searchTerm)
  );

  return (
    <div className="page-container">
      <div className="clinic-container">
        <div className="clinic-left">
          <div className="search-container">
            <input
              type="text"
              placeholder="🔍 Tìm kiếm phòng khám..."
              value={searchTerm}
              onChange={handleSearch}
              className="search-input"
            />
          </div>

          <hr className="divider" />

          <div className="clinic-form">
            <h4>Thông tin phòng khám</h4>
            <input
              type="text"
              name="name"
              placeholder="Tên phòng khám"
              value={form.name}
              onChange={handleChange}
            />
            <input
              type="text"
              name="address"
              placeholder="Địa chỉ"
              value={form.address}
              onChange={handleChange}
            />
            <input
              type="text"
              name="specialty"
              placeholder="Chuyên khoa"
              value={form.specialty}
              onChange={handleChange}
            />

            <div className="button-group">
              <button onClick={handleAdd} disabled={selectedIndex !== null}>
                Thêm
              </button>
              <button onClick={handleEdit} disabled={selectedIndex === null}>
                Sửa
              </button>
              <button onClick={handleDelete} disabled={selectedIndex === null}>
                Xóa
              </button>
            </div>
          </div>
        </div>

        <div className="clinic-list">
          <h4>Danh sách phòng khám</h4>
          {filteredClinics.map((clinic, index) => (
            <RoomListTag
              key={index}
              clinic={clinic}
              onSelect={() => handleSelect(index)}
              isSelected={selectedIndex === index}
            />
          ))}
        </div>
      </div>
      <Footer />
    </div>
  );
};

export default Room;