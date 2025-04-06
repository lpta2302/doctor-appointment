import AdminLogin from "../../../component/AdminLogin/AdminLogin";
import AdminLayout from "../../../layouts/admin/AdminLayout/AdminLayout";
import AdminClinic from "../../../pages/admin/AdminClinic/AdminClinic"
import AdminDepartment from "../../../pages/admin/AdminDepartment/AdminDepartment"
import AdminDoctor from "../../../pages/admin/AdminDoctor/AdminDoctor"
import AdminSchedule from "../../../pages/admin/AdminSchedule/AdminSchedule"
import { Routes, Route } from "react-router-dom";

export default function AdminRoutes() {
    return (
        <AdminLayout>
            <Routes>
                <Route path="/" element={<AdminLogin />} />
                <Route path="/clinic" element={<AdminClinic />} />
                <Route path="/department" element={<AdminDepartment />} />
                <Route path="/doctor" element={<AdminDoctor />} />
                <Route path="/schedule" element={<AdminSchedule />} />
            </Routes>
        </AdminLayout>
    );
}
