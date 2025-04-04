import { useEffect } from "react";
import { useLocation } from "react-router-dom";
import "./Home.css"
import slider from "../../../assets/image/slider.jpg"
import landing1 from "../../../assets/image/landing1.jpg"
import landing2 from "../../../assets/image/landing2.jpg"
import landing3 from "../../../assets/image/landing3.jpg"

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

                    <p>Lorem ipsum dolor, sit amet consectetur adipisicing elit.
                        Veniam vero velit libero voluptas blanditiis placeat doloribus
                        perferendis dolorem vel dolorum, voluptates iusto odit, id
                        facilis consectetur. Commodi quod quidem error.</p>

                    <button className="button">Booking</button>
                    <button className="button">More</button>
                </div>
            </div>

            {/* About */}
            <div id="about" className="row">
                <div className="col-3 text-center mt-5 about__slider">
                    <div>
                        <p>
                            Lorem ipsum dolor sit amet consectetur adipisicing elit.
                            Ipsa maiores illo dolor veritatis, tempore, nisi atque magnam,
                            perspiciatis totam officia repellat ducimus hic repellendus
                            sequi molestiae ullam porro non placeat maxime asperiores.
                            Soluta adipisci sapiente libero ratione quas perferendis
                            eligendi.
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
                            Lorem ipsum dolor sit amet consectetur adipisicing elit.
                            Ipsa maiores illo dolor veritatis, tempore, nisi atque magnam,
                            perspiciatis totam officia repellat ducimus hic repellendus
                            sequi molestiae ullam porro non placeat maxime asperiores.
                            Soluta adipisci sapiente libero ratione quas perferendis
                            eligendi.
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
                            Lorem ipsum dolor sit amet consectetur adipisicing elit.
                            Ipsa maiores illo dolor veritatis, tempore, nisi atque magnam,
                            perspiciatis totam officia repellat ducimus hic repellendus
                            sequi molestiae ullam porro non placeat maxime asperiores.
                            Soluta adipisci sapiente libero ratione quas perferendis
                            eligendi.
                        </p>
                    </div>

                    <div className="image first" style={{backgroundImage: `url('${landing1}')`}}>
                    </div>

                    <div className="image second" style={{backgroundImage: `url('${landing2}')`}}>
                    </div>
                </div>
            </div>
        </div>
    );
}
