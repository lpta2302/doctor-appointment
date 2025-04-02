import React, { useState } from "react";
import { Container, Row, Col, Form, Button, ListGroup } from "react-bootstrap";
import "../TimeAppointment/TimeAppointment.css";
import Footer from "../../../Footer/Footer.jsx";
import TagRoom from "../../../component-router/BookAppointment/TagRoom/TagRoom.jsx";

const TimeAppointment = () => {
  // State để quản lý danh sách phòng khám
  const [rooms, setRooms] = useState([
    {
      id: 1,
      roomName: "Phòng Nội Tổng Quát",
      examTime: "Thứ 2 - 15/04/2025, 08:00 - 10:30",
      doctorName: "TS.BS Nguyễn Văn A",
      location: "Lầu 3, Phòng 302",
    },
    {
      id: 2,
      roomName: "Phòng Nhi",
      examTime: "Thứ 3 - 16/04/2025, 09:00 - 11:30",
      doctorName: "BS Trần Thị B",
      location: "Lầu 2, Phòng 201",
    },
  ]);

  // State để quản lý form nhập liệu
  const [formData, setFormData] = useState({
    roomName: "",
    examTime: "",
    doctorName: "",
    location: "",
  });

  // State để quản lý tìm kiếm
  const [searchQuery, setSearchQuery] = useState("");
  const [selectedRoom, setSelectedRoom] = useState(null);

  // Xử lý thay đổi input trong form
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  // Xử lý tìm kiếm
  const handleSearch = () => {
    const filteredRooms = rooms.filter(
      (room) =>
        room.roomName.toLowerCase().includes(searchQuery.toLowerCase()) ||
        room.id.toString().includes(searchQuery)
    );
    setRooms(filteredRooms);
  };

  // Xử lý thêm phòng khám
  const handleAdd = () => {
    if (
      formData.roomName &&
      formData.examTime &&
      formData.doctorName &&
      formData.location
    ) {
      const newRoom = {
        id: rooms.length + 1,
        ...formData,
      };
      setRooms([...rooms, newRoom]);
      setFormData({ roomName: "", examTime: "", doctorName: "", location: "" });
    } else {
      alert("Vui lòng điền đầy đủ thông tin!");
    }
  };

  // Xử lý chọn phòng khám
  const handleSelectRoom = (room) => {
    setSelectedRoom(room);
    setFormData({
      roomName: room.roomName,
      examTime: room.examTime,
      doctorName: room.doctorName,
      location: room.location,
    });
  };

  // Xử lý sửa phòng khám
  const handleUpdate = () => {
    if (selectedRoom) {
      const updatedRooms = rooms.map((room) =>
        room.id === selectedRoom.id ? { ...room, ...formData } : room
      );
      setRooms(updatedRooms);
      setFormData({ roomName: "", examTime: "", doctorName: "", location: "" });
      setSelectedRoom(null);
    }
  };

  // Xử lý xóa phòng khám
  const handleDelete = () => {
    if (selectedRoom) {
      const updatedRooms = rooms.filter((room) => room.id !== selectedRoom.id);
      setRooms(updatedRooms);
      setFormData({ roomName: "", examTime: "", doctorName: "", location: "" });
      setSelectedRoom(null);
    }
  };

  return (
    <Container fluid className="booking-container">
      <Row className="justify-content-center align-items-center vh-100">
        <Col xs={12} md={10} lg={8} className="booking-box p-4">
          <Row>
            {/* Phần nhập thông tin và quản lý */}
            <Col xs={12} md={6} className="booking-form">
              <h2>Quản lý lịch khám</h2>

              {/* Ô tìm kiếm */}
              <Form.Group className="mb-3 search-group">
                <Form.Label>Tìm phòng (theo tên hoặc mã)</Form.Label>
                <div className="search-bar">
                  <Form.Control
                    type="text"
                    placeholder="Nhập tên hoặc mã phòng"
                    value={searchQuery}
                    onChange={(e) => setSearchQuery(e.target.value)}
                  />
                  <Button variant="primary" onClick={handleSearch}>
                    Tìm
                  </Button>
                </div>
              </Form.Group>

              {/* Form nhập thông tin phòng khám */}
              <Form>
                <Form.Group className="mb-3">
                  <Form.Label>Tên phòng</Form.Label>
                  <Form.Control
                    type="text"
                    placeholder="Nhập tên phòng"
                    name="roomName"
                    value={formData.roomName}
                    onChange={handleInputChange}
                  />
                </Form.Group>

                <Form.Group className="mb-3">
                  <Form.Label>Thời gian</Form.Label>
                  <Form.Control
                    type="text"
                    placeholder="Nhập thời gian (VD: Thứ 2 - 15/04/2025, 08:00 - 10:30)"
                    name="examTime"
                    value={formData.examTime}
                    onChange={handleInputChange}
                  />
                </Form.Group>

                <Form.Group className="mb-3">
                  <Form.Label>Bác sĩ</Form.Label>
                  <Form.Control
                    type="text"
                    placeholder="Nhập tên hoặc mã bác sĩ"
                    name="doctorName"
                    value={formData.doctorName}
                    onChange={handleInputChange}
                  />
                </Form.Group>

                <Form.Group className="mb-3">
                  <Form.Label>Địa điểm</Form.Label>
                  <Form.Control
                    type="text"
                    placeholder="Nhập địa điểm (VD: Lầu 3, Phòng 302)"
                    name="location"
                    value={formData.location}
                    onChange={handleInputChange}
                  />
                </Form.Group>

                {/* Các nút Thêm, Sửa, Xóa */}
                <div className="action-buttons">
                  <Button
                    variant="primary"
                    onClick={handleAdd}
                    disabled={selectedRoom !== null} // Làm mờ khi có phòng được chọn
                  >
                    Thêm
                  </Button>
                  <Button
                    variant="primary"
                    onClick={handleUpdate}
                    disabled={selectedRoom === null} // Làm mờ khi không có phòng được chọn
                  >
                    Sửa
                  </Button>
                  <Button
                    variant="primary"
                    onClick={handleDelete}
                    disabled={selectedRoom === null} // Làm mờ khi không có phòng được chọn
                  >
                    Xóa
                  </Button>
                </div>
              </Form>
            </Col>

            {/* Phần hiển thị danh sách phòng khám */}
            <Col xs={12} md={6} className="booking-info">
              <h3>Danh sách phòng khám</h3>
              <ListGroup>
                {rooms.length > 0 ? (
                  rooms.map((room) => (
                    <ListGroup.Item
                      key={room.id}
                      className={`room-item ${
                        selectedRoom && selectedRoom.id === room.id ? "selected" : ""
                      }`}
                      onClick={() => handleSelectRoom(room)}
                    >
                      <TagRoom
                        roomName={room.roomName}
                        examTime={room.examTime}
                        doctorName={room.doctorName}
                        location={room.location}
                      />
                    </ListGroup.Item>
                  ))
                ) : (
                  <p>Không tìm thấy phòng khám nào.</p>
                )}
              </ListGroup>
            </Col>
          </Row>
        </Col>
      </Row>
      <Footer />
    </Container>
  );
};

export default TimeAppointment;