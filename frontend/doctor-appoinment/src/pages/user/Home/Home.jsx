import { useEffect } from "react";
import { useLocation } from "react-router-dom";

export default function Home() {
    const location = useLocation();

    useEffect(() => {
        if (location.hash) {
            const element = document.getElementById(location.hash.substring(1));
            if (element) {
                element.scrollIntoView({ behavior: "smooth" });
            }
        }
    }, [location]);

    return (
        <div className="container">
            <div id="home" className="row">
                <div className="col-12 text-center mt-5">
                    <h1>Welcome to Booking App</h1>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p className="lead">
                        This is a simple booking app for you to book an
                        appointment with your doctor.
                    </p>
                </div>
            </div>

            {/* About */}
            <div id="about" className="row">
                <div className="col-12 text-center mt-5">
                    <h1>About Part</h1>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p className="lead">
                        This is a simple booking app for you to book an
                        appointment with your doctor.
                    </p>
                </div>
            </div>

            {/* Contact */}
            <div id="contact" className="row">
                <div className="col-12 text-center mt-5">
                    <h1>Contact Part</h1>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p className="lead">
                        This is a simple booking app for you to book an
                        appointment with your doctor.
                    </p>
                </div>
            </div>

            {/* Doctor */}
            <div id="doctor" className="row">
                <div className="col-12 text-center mt-5">
                    <h1>Doctor Part</h1>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p>e</p>
                    <p className="lead">
                        This is a simple booking app for you to book an
                        appointment with your doctor.
                    </p>
                </div>
            </div>
        </div>
    );
}
