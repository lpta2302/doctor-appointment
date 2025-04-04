import { useState, useEffect } from "react";
import RecordItem from "../../../component/RecordItem/RecordItem";
import "./AdminClinic.css";

const AdminClinic = () => {
    const [clinics, setClinics] = useState([]);
    const [entries, setEntries] = useState([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(1);
    const [searchParams, setSearchParams] = useState({ code: "", name: "" });
    const [showForm, setShowForm] = useState(false);
    const [newClinic, setNewClinic] = useState({
        code: "",
        name: "",
        specializationId: ""
    });
    const [specializations, setSpecializations] = useState([]);
    const pageSize = 15;

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
            setEntries(data.content.length > 0 ? Object.entries(data.content[0]) : []);
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

    const handleNewClinicChange = (e) => {
        setNewClinic({ ...newClinic, [e.target.name]: e.target.value });
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
            fetchClinics(page, searchParams);
            setShowForm(false);
        } catch (error) {
            console.error("Error adding clinic:", error);
        }
    };

    useEffect(() => {
        fetchClinics(page, searchParams);
        fetchSpecializations();
    }, [page, searchParams]);

    return (
        <div className="container-fluid text-center admin-clinic">
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
                <form onSubmit={handleSubmit} className="mb-3">
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
                    <button type="submit" className="btn btn-success">Add Clinic</button>
                    <button type="button" className="btn btn-secondary ml-2" onClick={() => setShowForm(false)}>Cancel</button>
                </form>
            )}
            <table className="table table-striped">
                <thead>
                    <tr>
                        {entries.map(([key], index) => (
                            <th key={index}>{key}</th>
                        ))}
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {clinics.map((clinic, index) => (
                        <tr key={index}>
                            <RecordItem data={clinic} />
                            <td>
                                <button className="btn btn-delete"><i className="fa-solid fa-trash"></i></button>
                                <button className="btn btn-update"><i className="fa-solid fa-pen-to-square"></i></button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
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
