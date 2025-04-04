import { useState, useEffect } from "react";
import RecordItem from "../../../component/RecordItem/RecordItem";
import "./AdminDoctor.css";

const AdminDoctor = () => {
    const [doctors, setDoctors] = useState([]);
    const [entries, setEntries] = useState([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(1);
    const [searchParams, setSearchParams] = useState({ code: "", name: "", specializationId: "" });
    const [showForm, setShowForm] = useState(false); // Hiển thị form thêm bác sĩ
    const [newDoctor, setNewDoctor] = useState({
        code: "",
        firstName: "",
        lastName: "",
        gender: "MALE",
        description: "",
        phoneNumber: "",
        workplace: "",
        qualification: "",
        dateOfBirth: "",
        yearsOfExperience: 0,
        specializationIds: []
    });
    const [specializations, setSpecializations] = useState([]); // Lưu danh sách specialization
    const pageSize = 3;

    const fetchDoctors = async (pageNumber, params) => {
        try {
            const query = new URLSearchParams({
                page: pageNumber,
                size: pageSize,
                ...(params.code && { code: params.code }),
                ...(params.name && { name: params.name }),
                ...(params.specializationId && { specializationId: params.specializationId })
            }).toString();

            const response = await fetch(`/api/v1/admin/doctors/search?${query}`);
            if (!response.ok) {
                throw new Error("Failed to fetch doctors");
            }
            const data = await response.json();
            setDoctors(data.content);
            setTotalPages(data.totalPages);
            setEntries(data.content.length > 0 ? Object.entries(data.content[0]) : []);
        } catch (error) {
            console.error("Error fetching doctors:", error);
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
        setPage(0); // Reset về trang đầu khi tìm kiếm
    };

    const handleNewDoctorChange = (e) => {
        const { name, value } = e.target;
        setNewDoctor((prev) => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSpecializationChange = (e) => {
        const value = e.target.value;
        setNewDoctor((prev) => ({
            ...prev,
            specializationIds: value ? [value] : [] // Chỉ chọn 1 specialization
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch("/api/v1/admin/doctors", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(newDoctor)
            });
            if (!response.ok) {
                throw new Error("Failed to add doctor");
            }
            // Fetch lại danh sách bác sĩ sau khi thêm thành công
            fetchDoctors(page, searchParams);
            setShowForm(false); // Đóng form sau khi thêm
        } catch (error) {
            console.error("Error adding doctor:", error);
        }
    };

    useEffect(() => {
        fetchDoctors(page, searchParams);
        fetchSpecializations(); // Lấy danh sách specialization khi load component
    }, [page, searchParams]);

    return (
        <div className="container-fluid text-center admin-doctor">
            <h1>Admin Doctor</h1>
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
                <input
                    type="text"
                    className="form-control mb-3"
                    name="specializationId"
                    placeholder="Search by specialization ID"
                    value={searchParams.specializationId}
                    onChange={handleSearchChange}
                />
            </div>
            <div className="container record-items">
                <button className="btn btn-primary mb-3" onClick={() => setShowForm(true)}>
                    Add New Doctor
                </button>

                {showForm && (
                    <form onSubmit={handleSubmit} className="mb-3">
                        <div className="form-group">
                            <input
                                type="text"
                                className="form-control"
                                name="code"
                                placeholder="Code"
                                value={newDoctor.code}
                                onChange={handleNewDoctorChange}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <input
                                type="text"
                                className="form-control"
                                name="firstName"
                                placeholder="First Name"
                                value={newDoctor.firstName}
                                onChange={handleNewDoctorChange}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <input
                                type="text"
                                className="form-control"
                                name="lastName"
                                placeholder="Last Name"
                                value={newDoctor.lastName}
                                onChange={handleNewDoctorChange}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <select
                                className="form-control"
                                name="specializationId"
                                value={newDoctor.specializationIds[0] || ""}
                                onChange={handleSpecializationChange}
                            >
                                <option value="">Select Specialization</option>
                                {specializations.map((specialization) => (
                                    <option key={specialization.id} value={specialization.id}>
                                        {specialization.name}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <div className="form-group">
                            <textarea
                                className="form-control"
                                name="description"
                                placeholder="Description"
                                value={newDoctor.description}
                                onChange={handleNewDoctorChange}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <input
                                type="text"
                                className="form-control"
                                name="phoneNumber"
                                placeholder="Phone Number"
                                value={newDoctor.phoneNumber}
                                onChange={handleNewDoctorChange}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <input
                                type="text"
                                className="form-control"
                                name="workplace"
                                placeholder="Workplace"
                                value={newDoctor.workplace}
                                onChange={handleNewDoctorChange}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <input
                                type="text"
                                className="form-control"
                                name="qualification"
                                placeholder="Qualification"
                                value={newDoctor.qualification}
                                onChange={handleNewDoctorChange}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <input
                                type="date"
                                className="form-control"
                                name="dateOfBirth"
                                value={newDoctor.dateOfBirth}
                                onChange={handleNewDoctorChange}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <input
                                type="number"
                                className="form-control"
                                name="yearsOfExperience"
                                placeholder="Years of Experience"
                                value={newDoctor.yearsOfExperience}
                                onChange={handleNewDoctorChange}
                                required
                            />
                        </div>
                        <button type="submit" className="btn btn-success">
                            Add Doctor
                        </button>
                        <button
                            type="button"
                            className="btn btn-secondary ml-2"
                            onClick={() => setShowForm(false)}
                        >
                            Cancel
                        </button>
                    </form>
                )}

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
                        {doctors.map((doctor, index) => (
                            <tr key={index}>
                                <RecordItem data={doctor} />
                                <td>
                                    <div className="col-1">
                                        <button className="btn btn-delete"><i className="fa-solid fa-trash"></i></button>
                                    </div>
                                    <div className="col-1">
                                        <button className="btn btn-update"><i className="fa-solid fa-pen-to-square"></i></button>
                                    </div>
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
        </div>
    );
};

export default AdminDoctor;
