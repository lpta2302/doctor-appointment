Write-Host "🏗️  Run Docker compose..."

# Vào thư mục chứa script build của backend
Set-Location ../backend/bin

# Cấp quyền cho script trong môi trường Unix (nếu chạy qua Git Bash/WSL)
# Bỏ qua nếu không cần, hoặc dùng Try-Catch để tránh lỗi
try {
    bash -c "chmod +x build-and-run.sh"
} catch {
    Write-Host "Skipping chmod, probably on Windows"
}

# Chạy script (qua bash nếu là shell Linux, hoặc chỉnh lại build-and-run.sh thành .ps1 nếu cần PowerShell)
bash build-and-run.sh

# Quay lại root backend
Set-Location ..

Write-Host "🚀 Starting Frontend..."

# Vào thư mục frontend
Set-Location ../../frontend/doctor-appointment

# Cài đặt và chạy frontend
npm install
npm run dev
