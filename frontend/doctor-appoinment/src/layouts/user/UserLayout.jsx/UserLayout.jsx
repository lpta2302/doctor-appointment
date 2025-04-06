import UserHeader from "../../../component/UserHeader/UserHeader";
import UserFooter from "../../../component/UserFooter/UserFooter";

const UserLayout = ({ children }) => {
    return (
        <>
            <UserHeader />
            <div className="" style={{ paddingTop: "85px" }}>
                {children}
            </div>
            <UserFooter />
        </>
    );
};

export default UserLayout;
