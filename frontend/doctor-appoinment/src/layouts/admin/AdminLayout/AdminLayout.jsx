import AdminHeader from "../../../component/AdminHeader/AdminHeader";

const AdminLayout = ({ children }) => {
    return (
        <>
            <AdminHeader />
            <div className="" style={{ paddingTop: "30px" }}>
                {children}
            </div>
        </>
    );
};

export default AdminLayout;
