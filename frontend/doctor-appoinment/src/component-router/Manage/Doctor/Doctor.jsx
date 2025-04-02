import React, { useState } from "react";
import "./Doctor.css";
import DoctorTag from "../Doctor/DocterTag/DoctorTag.jsx";
import Footer from "../../../Footer/Footer.jsx";

// Danh sách bác sĩ ban đầu
const initialDoctors = [
  { name: "Nguyễn Văn A", birthYear: "1980", specialty: "Tim mạch" },
  { name: "Trần Thị B", birthYear: "1985", specialty: "Nhi khoa" },
  { name: "Phạm Văn C", birthYear: "1978", specialty: "Chấn thương chỉnh hình" },
  { name: "Lê Thị D", birthYear: "1990", specialty: "Da liễu" },
  { name: "Hoàng Văn E", birthYear: "1975", specialty: "Tiêu hóa" },
  { name: "Đinh Thị F", birthYear: "1982", specialty: "Nội tiết" },
  { name: "Vũ Văn G", birthYear: "1988", specialty: "Thần kinh" },
  { name: "Bùi Thị H", birthYear: "1993", specialty: "Phụ sản" },
  { name: "Đoàn Văn I", birthYear: "1981", specialty: "Mắt" },
  { name: "Cao Thị K", birthYear: "1995", specialty: "Tai - Mũi - Họng" },
];

const Doctor = () => {
  // Quản lý trạng thái danh sách bác sĩ
  const [doctors, setDoctors] = useState(initialDoctors);
  // Quản lý trạng thái form nhập liệu
  const [form, setForm] = useState({ name: "", birthYear: "", specialty: "" });
  // Quản lý trạng thái tìm kiếm
  const [searchTerm, setSearchTerm] = useState("");
  // Quản lý chỉ số bác sĩ được chọn
  const [selectedIndex, setSelectedIndex] = useState(null);

  // Xử lý thay đổi giá trị trong form
  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
  };

  // Xử lý tìm kiếm bác sĩ
  const handleSearch = (e) => {
    setSearchTerm(e.target.value.toLowerCase());
  };

  // Thêm bác sĩ mới
  const handleAdd = () => {
    if (!form.name || !form.birthYear || !form.specialty) return;
    setDoctors([...doctors, form]);
    setForm({ name: "", birthYear: "", specialty: "" });
    setSelectedIndex(null);
  };

  // Sửa thông tin bác sĩ
  const handleEdit = () => {
    if (selectedIndex === null) return;
    const updated = [...doctors];
    updated[selectedIndex] = form;
    setDoctors(updated);
    setForm({ name: "", birthYear: "", specialty: "" });
    setSelectedIndex(null);
  };

  // Xóa bác sĩ
  const handleDelete = () => {
    if (selectedIndex === null) return;
    setDoctors(doctors.filter((_, idx) => idx !== selectedIndex));
    setForm({ name: "", birthYear: "", specialty: "" });
    setSelectedIndex(null);
  };

  // Chọn bác sĩ để chỉnh sửa
  const handleSelect = (index) => {
    setForm(doctors[index]);
    setSelectedIndex(index);
  };

  // Lọc danh sách bác sĩ theo từ khóa tìm kiếm
  const filteredDoctors = doctors.filter((doc) =>
    doc.name.toLowerCase().includes(searchTerm)
  );

  return (
    <div className="page-container">
      {/* Nội dung chính */}
      <div className="doctor-container">
        {/* Bên trái chứa tìm kiếm + form */}
        <div className="doctor-left">
          {/* Ô tìm kiếm */}
          <div className="search-container">
            <input
              type="text"
              placeholder="🔍 Tìm kiếm bác sĩ..."
              value={searchTerm}
              onChange={handleSearch}
              className="search-input"
            />
          </div>

          {/* Đường phân cách */}
          <hr className="divider" />

          {/* Form nhập bác sĩ */}
          <div className="doctor-form">
            <h4>Thông tin bác sĩ</h4>
            <input
              type="text"
              name="name"
              placeholder="Tên bác sĩ"
              value={form.name}
              onChange={handleChange}
            />
            <input
              type="number"
              name="birthYear"
              placeholder="Năm sinh"
              value={form.birthYear}
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

        {/* Bên phải chứa danh sách bác sĩ */}
        <div className="doctor-list">
          <h4>Danh sách bác sĩ</h4>
          {filteredDoctors.map((doc, index) => (
            <DoctorTag
              key={index}
              doctor={doc}
              onSelect={() => handleSelect(index)}
              isSelected={selectedIndex === index}
            />
          ))}
        </div>
      </div>

      {/* Footer */}
      <Footer />
    </div>
  );
};

export default Doctor;