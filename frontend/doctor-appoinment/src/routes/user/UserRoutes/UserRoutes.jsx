import Footer from "../../../component/UserFooter/UserFooter";
import UserLayout from "../../../layouts/user/UserLayout.jsx/UserLayout";
import Booking from "../../../pages/user/Booking/Booking";
import Home from "../../../pages/user/Home/Home";
import Schedule from "../../../pages/user/Schedule/Schedule";
import { Routes, Route } from "react-router-dom";

export default function UserRoutes() {
    return (
        <UserLayout>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/footer" element={<Footer />} />
                <Route path="/booking" element={<Booking />} />
                <Route path="/schedule" element={<Schedule />} />
            </Routes>
        </UserLayout>
    );
}
