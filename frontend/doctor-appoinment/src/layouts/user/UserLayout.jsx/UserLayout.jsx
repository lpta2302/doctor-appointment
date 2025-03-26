import UserHeader from "../../../component/UserHeader/UserHeader";

const UserLayout = ({ children }) => {
    return (
        <>
            <UserHeader />
            <div className="container" style={{ paddingTop: "100px" }}>
                {children}
            </div>
        </>
    );
};

export default UserLayout;
