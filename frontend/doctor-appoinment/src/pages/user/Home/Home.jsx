import { useEffect } from "react";
import { useLocation } from "react-router-dom";
import "./Home.css"
import slider from "../../../assets/image/slider.jpg"
import landing1 from "../../../assets/image/landing1.jpg"
import landing2 from "../../../assets/image/landing2.jpg"
import landing5 from "../../../assets/image/landing5.jpg"
import landing6 from "../../../assets/image/landing6.jpg"
import landing7 from "../../../assets/image/landing7.png"
import landing8 from "../../../assets/image/landing8.jpg"

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
        <div className="container-fluid">
            <div id="home" className="row">
                <div className="col-12 text-center" style={{ padding: 0 }}>
                    <img src={slider} alt="hihihi" width="100%" />
                </div>

                <div className="home__intro">
                    <h2>Making Health<br />Care Better Together</h2>

                    <p>Our clinic is a trusted healthcare destination, providing high-quality medical
                        services with experienced doctors and a friendly medical team. We are dedicated
                        to taking care of every customer with professionalism, compassion, and personalized
                        support for the best healthcare experience.</p>

                        <button className="button"><a href="/booking" style={{textDecoration: "none"}}>Booking</a></button>
                        <button className="button"><a href="/#about" style={{textDecoration: "none"}}>More</a></button>
                </div>
            </div>

            {/* About */}
            <div id="about" className="row">
                <div className="col-3 text-center mt-5 about__slider">
                    <div>
                        <p>
                            <span className="mb-3" style={{fontSize: "2rem", display: "block", fontWeight: 500}}>Đội ngũ bác sĩ lành nghề</span>
                            The team of doctors here are highly skilled professionals with many years of experience.
                            They always listen carefully and provide effective treatment plans, bringing absolute
                            eace of mind and trust to every customer.
                        </p>
                    </div>

                    <div className="image first" style={{backgroundImage: `url('${landing1}')`}}>
                    </div>

                    <div className="image second" style={{backgroundImage: `url('${landing2}')`}}>
                    </div>
                </div>

                <div className="col-3 text-center mt-5 about__slider">
                    <div>
                        <p>
                            <span className="mb-3" style={{fontSize: "2rem", display: "block", fontWeight: 500}}>Đội ngũ ý tá thân thiện</span>
                            The medical care team is not only experienced but also friendly and attentive in every service.
                            They are always ready to support and guide customers, helping them feel comfortable and reassured
                            throughout the examination and treatment process.
                        </p>
                    </div>

                    <div className="image first" style={{backgroundImage: `url('${landing5}')`}}>
                    </div>

                    <div className="image second" style={{backgroundImage: `url('${landing6}')`}}>
                    </div>
                </div>

                <div className="col-3 text-center mb-5 mt-5 about__slider">
                    <div>
                        <p>
                            <span className="mb-3" style={{fontSize: "2rem", display: "block", fontWeight: 500}}>Đội ngũ chăm sóc khác hàng tận tình</span>
                            The customer care team is professional, friendly, and always attentive to every customer’s needs.
                            They are ready to assist and respond to all inquiries quickly, ensuring a pleasant and satisfying
                            experience for everyone using the service.
                        </p>
                    </div>

                    <div className="image first" style={{backgroundImage: `url('${landing7}')`}}>
                    </div>

                    <div className="image second" style={{backgroundImage: `url('${landing8}')`}}>
                    </div>
                </div>
            </div>
        </div>
    );
}
