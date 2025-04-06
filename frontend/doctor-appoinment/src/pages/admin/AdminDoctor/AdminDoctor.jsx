import { useState, useEffect } from "react";
import RecordItem from "../../../component/RecordItem/RecordItem";
import "./AdminDoctor.css";

const AdminDoctor = () => {
    const [doctors, setDoctors] = useState([]);
    const [entries, setEntries] = useState([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(1);
    const [searchParams, setSearchParams] = useState({ code: "", name: "", specializationId: "" });
    const [showForm, setShowForm] = useState(false);
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
    const [isUpdate, setIsUpdate] = useState({update: false, id: null});
    const [specializations, setSpecializations] = useState([]);
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
        setPage(0);
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
            specializationIds: value ? [value] : []
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

            alert("Add successfully !");
            fetchDoctors(page, searchParams);
            setNewDoctor({
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
            setShowForm(false);
        } catch (error) {
            alert("Add failed !");
        }
    };

    const handleUpdate = async (id) => {
        const confirmDelete = window.confirm("Are you sure you want to update this doctor?");
        if (!confirmDelete) return;

        try {
            const response = await fetch(`/api/v1/admin/doctors/${id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(newDoctor)
            });
            if (!response.ok) {
                throw new Error("Failed to add doctor");
            }

            alert("Update successfully !");
            fetchDoctors(page, searchParams);
            setNewDoctor({
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
            setShowForm(false);
        } catch (error) {
            alert("Update failed !");
        }
    };

    const handleUpdateClick = (doctorId) => {
        setShowForm(true);
        setIsUpdate({update: true, id: doctorId});;
        getDoctor(doctorId);
    };

    const getDoctor = async (id) => {
        try {
            const response = await fetch(`/api/v1/admin/doctors/${id}`, {
                method: "GET",
            });
            if (!response.ok) {
                throw new Error("Failed to add doctor");
            }
            const data = await response.json();
            setNewDoctor({
                code: data.code,
                firstName: data.firstName,
                lastName:  data.lastName,
                gender: "MALE",
                description: data.description,
                phoneNumber: data.phoneNumber,
                workplace: data.workplace,
                qualification: data.qualification,
                dateOfBirth: data.dateOfBirth,
                yearsOfExperience: data.yearsOfExperience,
                specializationIds: specializations.map(s=>s.id)
            });
        } catch (error) {
            alert("Update failed !");
        }
    };

    const deleteDoctor = async (doctorId) => {
        const confirmDelete = window.confirm("Are you sure you want to delete this doctor?");
        if (!confirmDelete) return;

        try {
            const response = await fetch(`/api/v1/admin/doctors/${doctorId}`, {
                method: "DELETE"
            });
            if (!response.ok) {
                throw new Error("Failed to delete doctor");
            }
            alert("Delete successfully !");
            fetchDoctors(page, searchParams);
        } catch (error) {
            alert("Delete failed !");
        }
    };

    useEffect(() => {
        fetchDoctors(page, searchParams);
        fetchSpecializations();
    }, [page, searchParams]);

    console.log(isUpdate);

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
                <button className="btn btn-primary mb-3" onClick={() => {
                    setShowForm(true);
                    setIsUpdate({update: false, id: null});
                }}>
                    Add New Doctor
                </button>

                {showForm && (
                    <div className="modal-overlay" onClick={() => setShowForm(false)}>
                        <form className="modal-content" onClick={(e) => e.stopPropagation()} onSubmit={isUpdate.update ? () => {handleUpdate(isUpdate.id)} : handleSubmit}>
                            <button className="modal-close" onClick={() => setShowForm(false)}>&times;</button>
                            <h3>Add New Department</h3>
                            <div className="row justify-content-center">
                                <div className="col">
                                    <input
                                        type="text"
                                        className="form-control"
                                        name="code"
                                        placeholder="Code"
                                        value={newDoctor.code}
                                        onChange={handleNewDoctorChange}
                                        required
                                    />
                                    <input
                                        type="text"
                                        className="form-control"
                                        name="firstName"
                                        placeholder="First Name"
                                        value={newDoctor.firstName}
                                        onChange={handleNewDoctorChange}
                                        required
                                    />
                                    <input
                                        type="text"
                                        className="form-control"
                                        name="lastName"
                                        placeholder="Last Name"
                                        value={newDoctor.lastName}
                                        onChange={handleNewDoctorChange}
                                        required
                                    />
                                    <select
                                        className="form-control"
                                        name="gender"
                                        value={newDoctor.gender}
                                        onChange={handleNewDoctorChange}
                                        required
                                    >
                                        <option value="MALE">MALE</option>
                                        <option value="FEMALE">FEMALE</option>
                                    </select>
                                    <input
                                        type="text"
                                        className="form-control"
                                        name="description"
                                        placeholder="Description"
                                        value={newDoctor.description}
                                        onChange={handleNewDoctorChange}
                                        required
                                    />
                                </div>
                                <div className="col">
                                    <input
                                        type="text"
                                        className="form-control"
                                        name="phoneNumber"
                                        placeholder="Phone Number"
                                        value={newDoctor.phoneNumber}
                                        onChange={handleNewDoctorChange}
                                        required
                                    />
                                    <input
                                        type="text"
                                        className="form-control"
                                        name="workplace"
                                        placeholder="Workplace"
                                        value={newDoctor.workplace}
                                        onChange={handleNewDoctorChange}
                                        required
                                    />
                                    <input
                                        type="text"
                                        className="form-control"
                                        name="qualification"
                                        placeholder="Qualification"
                                        value={newDoctor.qualification}
                                        onChange={handleNewDoctorChange}
                                        required
                                    />
                                    <input
                                        type="date"
                                        className="form-control"
                                        name="dateOfBirth"
                                        value={newDoctor.dateOfBirth}
                                        onChange={handleNewDoctorChange}
                                        required
                                    />
                                    <input
                                        type="number"
                                        className="form-control"
                                        name="yearsOfExperience"
                                        placeholder="Years of Experience"
                                        value={newDoctor.yearsOfExperience}
                                        onChange={handleNewDoctorChange}
                                        required
                                    />

                                    <select
                                        className="form-control"
                                        name="specializationId"
                                        value={(newDoctor.specializationIds[0]) || ""}
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
                            </div>
                            <button type="" className="btn btn-success">Submit</button>
                        </form>

                    </div>
                )}

                {doctors.length > 0 ? (
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
                                            <button className="btn btn-delete" onClick={() => { deleteDoctor(doctor.id) }}><i className="fa-solid fa-trash"></i></button>
                                        </div>
                                        <div className="col-1">
                                            <button className="btn btn-update" onClick={() => {
                                                handleUpdateClick(doctor.id)
                                            }}><i className="fa-solid fa-pen-to-square"></i></button>
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
        </div >
    );
};

export default AdminDoctor;
