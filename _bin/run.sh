#!/bin/sh

echo "🏗️  Run Docker compose..."

cd "../backend/bin"
chmod +x build-and-run.sh
./build-and-run.sh
cd ../

echo "🚀 Starting Frontend..."
cd "../frontend/doctor-appoinment" 
npm install 
npm run dev
