# 🏥 Doctor Appointment Application

A simple microservices application for booking clinic appointments with doctor availability and admin scheduling control.

---

## ✨ Key Features

- Book appointments with available clinics and time slots  
- View doctor, clinic, specialization, and schedule information  
- Admin panel for managing doctor shifts and scheduling

---

## 🛠️ Technologies

- **Frontend**: React + Tailwind CSS  
- **Backend**: Spring Boot + PostgreSQL  
- **Others**: Kafka, Docker, etc.

---

* Note: Make sure Docker is running and the following ports are available: 
* 8888, 8761, 8222, 8070, 8071, 8072, 8073, 5432, 9092, 5174. 

---

## 🚀 Installation

### 📥 Clone the repository
```bash
git clone https://github.com/lpta2302/doctor-appointment.git
```
Then open the repo folder in your favorite terminal  
(PowerShell, Git Bash, or ideally a Unix/Linux environment)

#### 📌 In PowerShell
cd _bin  
.\run.ps1

#### 🐧 In Git Bash or Unix/Linux environment
cd _bin  
chmod +x run.sh  
./run.sh

#### 🧩 OR Manual Setup
📦 Frontend

cd frontend  
npm install  
npm run dev

⚙️ Backend

cd backend  
(Package each service and server then start with Docker)  
docker compose up
