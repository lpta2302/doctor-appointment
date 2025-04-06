import { NavLink, Link } from "react-router-dom"
import logo_bookingapp from "../../assets/image/logo_bookingapp.png"
import "./AdminHeader.css"

const AdminHeader = () => {
    return (
        <nav className="navbar navbar-expand-lg navbar-light shadow-sm admin-header">
            <div className="container">
                {/* Logo */}
                <Link className="navbar-brand logo" to="/admin">
                    <img src={logo_bookingapp} alt="Logo" height="80" />
                </Link>

                {/* Toggle button cho mobile */}
                <button
                    className="navbar-toggler"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#navbarNav"
                    aria-controls="navbarNav"
                    aria-expanded="false"
                    aria-label="Toggle navigation"
                >
                    <span className="navbar-toggler-icon"></span>
                </button>


                <div className="collapse navbar-collapse" id="navbarNav">
                    <ul className="navbar-nav ms-auto">
                        <li className="nav-item mx-3">
                            <NavLink className="link nav-link fs-4" to="/admin/clinic">
                                Clinic
                            </NavLink>
                        </li>

                        <li className="nav-item mx-3">
                            <NavLink className="link nav-link fs-4" to="/admin/department">
                                Department
                            </NavLink>
                        </li>

                        <li className="nav-item mx-3">
                            <NavLink
                                className="nav-link fs-4 button"
                                to="/admin/doctor"
                            >
                                Doctor
                            </NavLink>
                        </li>

                        <li className="nav-item">
                            <NavLink
                                className="nav-link fs-4 button"
                                to="/admin/schedule"
                            >
                                Schedule
                            </NavLink>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    )
}

export default AdminHeader;