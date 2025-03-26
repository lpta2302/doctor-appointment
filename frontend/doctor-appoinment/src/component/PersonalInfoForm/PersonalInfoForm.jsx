const PersonalInfoForm = ({formData, handleChange, goNext}) => {
    return (
        <form action="" className="row">
            <div className="col-lg-6">
                <div className="my-3 mx-2">
                    <label htmlFor="name" className="form-label">
                        Name:
                    </label>

                    <input
                        type="text"
                        className="form-control"
                        id="name"
                        placeholder="Enter name"
                        name="name"
                        onChange={handleChange}
                        value={formData.form.name}
                        required
                    />
                </div>

                <div className="my-3 mx-2">
                    <label htmlFor="phone" className="form-label">
                        Phone Number:
                    </label>

                    <input
                        type="text"
                        className="form-control"
                        id="phone"
                        placeholder="Enter phone number"
                        name="phone"
                        onChange={handleChange}
                        value={formData.form.phone}
                        required
                    />
                </div>

                <div className="my-3 mx-2">
                    <label htmlFor="email" className="form-label">
                        Email:
                    </label>
                    <input
                        type="email"
                        className="form-control"
                        id="email"
                        placeholder="Enter email"
                        name="email"
                        onChange={handleChange}
                        value={formData.form.email}
                        required
                    />
                </div>

                <div className="my-3 mx-2">
                    <label htmlFor="location" className="form-label">
                        Location:
                    </label>
                    <input
                        type="text"
                        className="form-control"
                        id="location"
                        placeholder="Enter location"
                        name="location"
                        onChange={handleChange}
                        value={formData.form.location}
                        required
                    />
                </div>
            </div>

            <div className="col-lg-6">
                <div className="my-3 mx-2">
                    <label htmlFor="description" className="form-label">
                        Description:
                    </label>
                    <textarea
                        type="text"
                        className="form-control desc-input"
                        id="description"
                        placeholder="Enter description"
                        name="description"
                        onChange={handleChange}
                        value={formData.form.description}
                    />
                </div>
            </div>

            <button type="submit" className="btn-submit m-3" onClick={goNext}>
                Next
            </button>
        </form>
    )
}

export default PersonalInfoForm;