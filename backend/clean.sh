#!/bin/sh

echo "🚀 Stop Docker Compose and Remove volume..."
docker compose down -v

echo "🏗️  Remove all services..."
SERVICES=("config-server" "discovery-server" "gateway" "clinic-service" "doctor-service")  # Danh sách thư mục chứa các service

for SERVICE in "${SERVICES[@]}"; do
  echo "🔨 Removing $SERVICE..."
  docker rmi backend-$SERVICE
done

echo "✅ All services have been removed successfully!"
