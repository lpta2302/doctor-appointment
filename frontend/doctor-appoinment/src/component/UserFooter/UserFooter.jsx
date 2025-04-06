import React from "react";
import {Link} from "react-router-dom"
import './UserFooter.css'
import logo_bookingapp from "../../assets/image/logo_bookingapp.png";

const Footer = () => {
    return (
        <footer id="footer">
            <div className="container">
                <div className="row">
                    <div className="col-4 footer__logo">
                        <Link className="navbar-brand logo" to="/#home">
                            <img src={logo_bookingapp} alt="Logo" height="80" />
                        </Link>
                    </div>

                    <div className="col-7 footer__infomation">
                        <div className="row">
                            <div className="col-1">
                                <a href="" className="nav-link sub-nav">AboutUs</a>
                                <a href="" className="nav-link item">Project</a>
                                <a href="" className="nav-link item">Track Record</a>
                                <a href="" className="nav-link item">Pricing</a>
                                <a href="" className="nav-link item">Services</a>
                                <a href="" className="nav-link item">Professional Worker</a>
                            </div>

                            <div className="col-1">
                                <a href="" className="nav-link sub-nav">Pricing</a>
                                <a href="" className="nav-link item">About Product</a>
                                <a href="" className="nav-link item">Management</a>
                            </div>

                            <div className="col-1">
                                <a href="" className="nav-link sub-nav">Media</a>
                                <a href="" className="nav-link item">Privacy Police</a>
                                <a href="" className="nav-link item">Terms & Service</a>
                            </div>

                            <div className="col-1">
                                <a href="" className="nav-link sub-nav">Contact</a>
                                <a href="" className="nav-link item">rubik1x11@gmail.com</a>
                                <a href="" className="nav-link item">0977172577</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </footer>
    );
}

export default Footer;