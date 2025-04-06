#!/bin/sh

echo "🏗️  Run Docker compose..."

cd "../backend" && docker compose up -d

echo "🚀 Starting Frontend..."
cd "../frontend/doctor-appoinment" && npm install && npm run dev
