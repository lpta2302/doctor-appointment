import React from "react";
import { Link, NavLink } from "react-router-dom";
import logo_bookingapp from "../../assets/image/logo_bookingapp.png";
import "./UserHeader.css";

function UserHeader() {
    return (
        <nav className="navbar navbar-expand-lg navbar-light shadow-sm header">
            <div className="container" style={{ margin: "0 200px" }}>
                {/* Logo */}
                <Link className="navbar-brand logo" to="/#home">
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

                {/* Menu */}
                <div className="collapse navbar-collapse" id="navbarNav">
                    <ul className="navbar-nav ms-auto">
                        <li className="nav-item mx-3">
                            <Link className="link nav-link fs-4" to="/#about">
                                About
                            </Link>
                        </li>

                        <li className="nav-item mx-3">
                            <Link className="link nav-link fs-4" to="/#footer">
                                Contact
                            </Link>
                        </li>

                        <li className="nav-item mx-3">
                            <Link
                                className="nav-link fs-4 button"
                                to="/booking"
                            >
                                Booking
                            </Link>
                        </li>

                        <li className="nav-item">
                            <Link
                                className="nav-link fs-4 button"
                                to="/schedule"
                            >
                                Schedule
                            </Link>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    );
}

export default UserHeader;
