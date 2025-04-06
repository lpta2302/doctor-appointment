Write-Host "🏗️  Run Docker compose..."
cd ../backend
docker compose up -d

Write-Host "🚀 Starting Frontend..."
cd ../frontend/doctor-appoinment
npm install
npm run dev
