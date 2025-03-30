import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Row, Col } from "reactstrap";
import Header from "../src/Header/Header.jsx";
import Wellcome from "./component-router/Wellcome/Wellcome.jsx";
import BookingAppointment from "./component-router/BookAppointment/BookingAppointment.jsx";
import LookingInformation from "./component-router/LookingInformation/LookingInformation.jsx";
import Docter from "./component-router/Manage/Doctor/Doctor.jsx"
import Room from "./component-router/Manage/Room/Room.jsx"

const EditSchedule = () => <p>Chỉnh sửa Lịch khám</p>;

function App() {
  return (
    <Router>
      <div className="app-container">
        {/* Header */}
        <Row className="header">
          <Col xs="12">
            <Header />
          </Col>
        </Row>

        {/* Body (Routes) */}
        <Row className="body">
          <Col xs="12" className="bg-light">
            <Routes>
              <Route path="/" element={<Wellcome />} />
              <Route path="/dat-lich-hen" element={<BookingAppointment />} />
              <Route path="/thong-tin" element={<LookingInformation />} />
              <Route path="/thong-ke" element={<p>THỐNG KÊ</p>} />

              {/* Các route mới */}
              <Route path="/quan-ly/bac-si" element={< Docter />} />
              <Route path="/quan-ly/phong-kham" element={<Room />} />
              <Route path="/quan-ly/lich-kham" element={<EditSchedule />} />
            </Routes>
          </Col>
        </Row>
      </div>
    </Router>
  );
}

export default App;
