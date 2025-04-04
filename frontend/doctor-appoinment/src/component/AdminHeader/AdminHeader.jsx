import { Link } from "react-router-dom"

const AdminHeader = () => {
    return (
        <nav className="navbar navbar-expand-lg navbar-light shadow-sm admin-header">
            <div className="container">
                <div className="collapse navbar-collapse" id="navbarNav">
                    <ul className="navbar-nav ms-auto">
                        <li className="nav-item mx-3">
                            <Link className="link nav-link fs-4" to="/admin/clinic">
                                Clinic
                            </Link>
                        </li>

                        <li className="nav-item mx-3">
                            <Link className="link nav-link fs-4" to="/admin/department">
                                Department
                            </Link>
                        </li>

                        <li className="nav-item mx-3">
                            <Link
                                className="nav-link fs-4 button"
                                to="/admin/doctor"
                            >
                                Doctor
                            </Link>
                        </li>

                        <li className="nav-item">
                            <Link
                                className="nav-link fs-4 button"
                                to="/admin/schedule"
                            >
                                Schedule
                            </Link>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    )
}

export default AdminHeader;