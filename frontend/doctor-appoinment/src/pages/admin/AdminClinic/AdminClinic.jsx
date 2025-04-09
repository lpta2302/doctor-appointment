import { useState, useEffect } from "react";
import RecordItem from "../../../component/RecordItem/RecordItem";
import "./AdminClinic.css";

const AdminClinic = () => {
    const [clinics, setClinics] = useState([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(1);
    const [searchParams, setSearchParams] = useState({ code: "", name: "" });
    const [showForm, setShowForm] = useState(false);
    const [newClinic, setNewClinic] = useState({
        code: "",
        name: "",
        specializationId: "",
        specializationName: ""
    });
    const [specializations, setSpecializations] = useState([]);
    const pageSize = 3;

    const fetchClinics = async (pageNumber, params) => {
        try {
            const query = new URLSearchParams({
                page: pageNumber,
                size: pageSize,
                ...(params.code && { code: params.code }),
                ...(params.name && { name: params.name })
            }).toString();

            const response = await fetch(`/api/v1/admin/clinics?${query}`);
            if (!response.ok) {
                throw new Error("Failed to fetch clinics");
            }
            const data = await response.json();
            setClinics(data.content);
            setTotalPages(data.totalPages);
        } catch (error) {
            console.error("Error fetching clinics:", error);
        }
    };

    const fetchSpecializations = async () => {
        try {
            const response = await fetch("/api/v1/admin/specializations");
            if (!response.ok) {
                throw new Error("Failed to fetch specializations");
            }
            const data = await response.json();
            setSpecializations(data.content);
        } catch (error) {
            console.error("Error fetching specializations:", error);
        }
    };

    const handleSearchChange = (e) => {
        setSearchParams({ ...searchParams, [e.target.name]: e.target.value });
        setPage(0);
    };

    const handleNewClinicChange = async (e) => {
        if (e.target.name === "specializationId") {
            const selectedName = e.target.options[e.target.selectedIndex].text;
            setNewClinic({
                ...newClinic,
                [e.target.name]: e.target.value,
                specializationName: selectedName
            });
        } else {
            setNewClinic({ ...newClinic, [e.target.name]: e.target.value });
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch("/api/v1/admin/clinics", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(newClinic)
            });
            if (!response.ok) {
                throw new Error("Failed to add clinic");
            }
            alert("Clinic added successfully!");
            fetchClinics(page, searchParams);
            setNewClinic({
                code: "",
                name: "",
                specializationId: "",
                specializationName: ""
            });
            setShowForm(false);
        } catch (error) {
            alert("Failed to add clinic !");
        }
    };

    const deleteClinic = async (id) => {
        if (!window.confirm("Are you sure you want to delete this clinic?")) {
            return;
        }
    
        try {
            const response = await fetch("/api/v1/admin/clinics/"+id, {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json"
                }
            });
    
            if (!response.ok) {
                throw new Error("Failed to delete clinic");
            }
    
            alert("Clinic deleted successfully!");
            fetchClinics(page, searchParams);
        // eslint-disable-next-line no-unused-vars
        } catch (e) {
            alert("Failed to delete clinic!");
        }
    };
    

    useEffect(() => {
        fetchClinics(page, searchParams);
        fetchSpecializations();
    }, [page, searchParams]);

    return (
        <div className="container text-center admin-clinic">
            <h1>Admin Clinic</h1>
            <div className="container">
                <input
                    type="text"
                    className="form-control mb-3"
                    name="code"
                    placeholder="Search by code"
                    value={searchParams.code}
                    onChange={handleSearchChange}
                />
                <input
                    type="text"
                    className="form-control mb-3"
                    name="name"
                    placeholder="Search by name"
                    value={searchParams.name}
                    onChange={handleSearchChange}
                />
            </div>
            <button className="btn btn-primary mb-3" onClick={() => setShowForm(true)}>
                Add New Clinic
            </button>

            {showForm && (
                <div className="modal-overlay" onClick={() => setShowForm(false)}>
                    <form className="modal-content" onClick={(e) => e.stopPropagation()} /*onSubmit={isUpdate.update ? () => { handleUpdate(isUpdate.id) } : }*/ onSubmit={handleSubmit}>
                        <button className="modal-close" onClick={() => setShowForm(false)}>&times;</button>
                        <h3>Add New Department</h3>
                        <input
                            type="text"
                            className="form-control mb-2"
                            name="code"
                            placeholder="Clinic Code"
                            value={newClinic.code}
                            onChange={handleNewClinicChange}
                            required
                        />
                        <input
                            type="text"
                            className="form-control mb-2"
                            name="name"
                            placeholder="Clinic Name"
                            value={newClinic.name}
                            onChange={handleNewClinicChange}
                            required
                        />
                        <select
                            className="form-control mb-2"
                            name="specializationId"
                            value={newClinic.specializationId}
                            onChange={handleNewClinicChange}
                            required
                        >
                            <option value="">Select Specialization</option>
                            {specializations.map((spec) => (
                                <option key={spec.id} value={spec.id}>{spec.name}</option>
                            ))}
                        </select>
                        <button className="btn btn-success">Submit</button>
                    </form>

                </div>
            )}

            {clinics.length > 0 ? (
                <table className="table table-striped">
                    <thead>
                        <tr>
                            <th>id</th>
                            <th>code</th>
                            <th>name</th>
                            <th>specialization name</th>
                            <th>specialization id</th>
                            <th>action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {clinics.map((clinic, index) => (
                            <tr key={index}>
                                <RecordItem data={{ id: clinic.id, code: clinic.code, name: clinic.name, specializationName: clinic.specialization?.name || "not assigned", specializationId: clinic.specialization?.id || "not assigned" }} />
                                <td>
                                    <button className="btn btn-delete" onClick={() => {deleteClinic(clinic.id)}}><i className="fa-solid fa-trash"></i></button>
                                    <button className="btn btn-update"><i className="fa-solid fa-pen-to-square"></i></button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            ) : (
                <div className="container no-data text-center">
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
    );
};

export default AdminClinic;
