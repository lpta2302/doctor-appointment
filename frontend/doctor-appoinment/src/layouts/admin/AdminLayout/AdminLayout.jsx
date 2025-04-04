import AdminHeader from "../../../component/AdminHeader/AdminHeader";

const AdminLayout = ({ children }) => {
    return (
        <>
            <AdminHeader />
            <div className="" style={{ paddingTop: "10px" }}>
                {children}
            </div>
        </>
    );
};

export default AdminLayout;
