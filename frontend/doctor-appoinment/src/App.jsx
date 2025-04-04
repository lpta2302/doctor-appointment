import { Route, Router, Routes } from "react-router-dom";
import UserRoutes from "./routes/user/UserRoutes/UserRoutes";
import AdminRoutes from "./routes/admin/AdminRoutes/AdminRoutes";

function App() {
    return (
        <Routes>
            {/* Đường dẫn cho User */}
            <Route path="/*" element={<UserRoutes />} />

            {/* Đường dẫn cho Admin */}
            <Route path="/admin/*" element={<AdminRoutes />} />
        </Routes>
    );
}

export default App;
