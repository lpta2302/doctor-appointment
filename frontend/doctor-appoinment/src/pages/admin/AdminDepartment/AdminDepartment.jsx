import { useState, useEffect } from "react";
import RecordItem from "../../../component/RecordItem/RecordItem";
import "./AdminDepartment.css";

const AdminDepartment = () => {
    const [departments, setDepartments] = useState([]);
    const [entries, setEntries] = useState([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(1);
    const [searchTerm, setSearchTerm] = useState("");
    const [showAddForm, setShowAddForm] = useState(false);
    const [showUpdateForm, setShowUpdateForm] = useState({show: false, id: null});
    const [newDepartment, setNewDepartment] = useState({ code: "", name: "", price: "" });
    const [dataUpdateDepartment, setDataUpdateDepartment] = useState({ name: "", price: "" });
    const pageSize = 5;

    const getAllDepartment = async (pageNumber, search = "") => {
        try {
            const url = search
                ? `/api/v1/admin/specializations/search?page=${pageNumber}&size=${pageSize}&name=${search}&code=${search}`
                : `/api/v1/admin/specializations?page=${pageNumber}&size=${pageSize}`;

            const response = await fetch(url);
            if (!response.ok) {
                throw new Error("Failed to fetch departments");
            }
            const data = await response.json();
            setDepartments(data.content);
            setTotalPages(data.totalPages);
            setEntries(data.content.length > 0 ? Object.entries(data.content[0]) : []);
        } catch (error) {
            console.error("Error fetching departments:", error);
        }
    };

    const addDepartment = async () => {
        try {
            const response = await fetch("/api/v1/admin/specializations", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(newDepartment)
            });
            if (!response.ok) {
                throw new Error("Failed to add department");
            }
            setShowAddForm(false);
            setNewDepartment({ code: "", name: "", price: "" });
            alert("Add successfully !");
            getAllDepartment(page, searchTerm);
        } catch (error) {
            alert("ID already exists !");
        }
    };

    const updateDepartment = async (id) => {
        try {
            const response = await fetch(`/api/v1/admin/specializations/${id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(dataUpdateDepartment)
            });
            if (!response.ok) {
                throw new Error("Failed to update department");
            }
            setShowUpdateForm({...showUpdateForm, show: false});
            setDataUpdateDepartment({ name: "", price: "" });
            alert("Add successfully !");
            getAllDepartment(page, searchTerm);
        } catch (error) {
            alert("ID already exists !");
        }
    };

    const removeDepartment = async (id) => {
        try {
            const response = await fetch(`/api/v1/admin/specializations/${id}`, {
                method: "DELETE"
            });

            if (!response.ok) {
                throw new Error("Failed to delete department");
            }

            alert("Delete successfully !");
            getAllDepartment(page, searchTerm);
        } catch (error) {
            console.alert("Cannot delete department !");
        }
    }

    useEffect(() => {
        getAllDepartment(page, searchTerm);
    }, [page, searchTerm]);

    return (
        <div className="container-fluid text-center admin-department">
            <h1>Admin Department</h1>
            <div className="container">
                <input
                    type="text"
                    className="form-control mb-3"
                    placeholder="Search by code..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                />
                <button className="btn btn-primary mb-3" onClick={() => setShowAddForm(!showAddForm)}>Add Item</button>
            </div>

            {showAddForm && (
                <div className="modal-overlay" onClick={() => setShowAddForm(false)}>
                    <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                        <button className="modal-close" onClick={() => setShowAddForm(false)}>&times;</button>
                        <h3>Add New Department</h3>
                        <input
                            type="text"
                            className="form-control mb-2"
                            placeholder="Code"
                            value={newDepartment.code}
                            onChange={(e) => setNewDepartment({ ...newDepartment, code: e.target.value })}
                        />
                        <input
                            type="text"
                            className="form-control mb-2"
                            placeholder="Name"
                            value={newDepartment.name}
                            onChange={(e) => setNewDepartment({ ...newDepartment, name: e.target.value })}
                        />
                        <input
                            type="number"
                            className="form-control mb-2"
                            placeholder="Price"
                            value={newDepartment.price}
                            onChange={(e) => setNewDepartment({ ...newDepartment, price: e.target.value })}
                        />
                        <button className="btn btn-success" onClick={addDepartment}>Submit</button>
                    </div>
                </div>
            )}

            {showUpdateForm.show && (
                <div className="modal-overlay" onClick={() => setShowUpdateForm({...showUpdateForm, show: false})}>
                    <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                        <button className="modal-close" onClick={() => setShowUpdateForm({...showUpdateForm, show: false})}>&times;</button>
                        <h3>Update Department</h3>
                        <input
                            type="text"
                            className="form-control mb-2"
                            placeholder="Name"
                            value={dataUpdateDepartment.name}
                            onChange={(e) => setDataUpdateDepartment({ ...dataUpdateDepartment, name: e.target.value })}
                        />
                        <input
                            type="number"
                            className="form-control mb-2"
                            placeholder="Price"
                            value={dataUpdateDepartment.price}
                            onChange={(e) => setDataUpdateDepartment({ ...dataUpdateDepartment, price: e.target.value })}
                        />
                        <button className="btn btn-success" onClick={() => updateDepartment(showUpdateForm.id)}>Submit</button>
                    </div>
                </div>
            )}


            <div className="container record-item">
                {departments.length > 0 ? (
                    <table className="table table-striped">

                        <thead className="field">
                            <tr>
                                {entries.map(([key], index) => (
                                    <th key={index}>{key}</th>
                                ))}
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody className="data">
                            {departments.map((department, index) => (
                                <tr key={index}>
                                    <RecordItem data={department} />
                                    <td>
                                        <div className="row control" style={{ justifyContent: "center" }}>
                                            <div className="col-1 m-0 p-0">
                                                <button className="btn btn-delete" onClick={() => { removeDepartment(department.id) }}><i className="fa-solid fa-trash"></i></button>
                                            </div>
                                            <div className="col-1 m-0 p-0">
                                                <button className="btn btn-update" onClick={() => { setShowUpdateForm({...showUpdateForm, show: true}) }}><i className="fa-solid fa-pen-to-square"></i></button>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                ) : (
                    <div className="no-data text-center">
                        <p>Không có data</p>
                    </div>
                )}
                <nav>
                    <ul className="pagination justify-content-center">
                        <li className={`page-item ${page === 0 ? "disabled" : ""}`}>
                            <button className="page-link" onClick={() => setPage(page - 1)}>Previous</button>
                        </li>
                        {[...Array(totalPages)].map((_, index) => (
                            <li key={index} className={`page-item ${index === page ? "active" : ""}`}>
                                <button className="page-link" onClick={() => setPage(index)}>{index + 1}</button>
                            </li>
                        ))}
                        <li className={`page-item ${page === totalPages - 1 ? "disabled" : ""}`}>
                            <button className="page-link" onClick={() => setPage(page + 1)}>Next</button>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
    );
};

export default AdminDepartment;
