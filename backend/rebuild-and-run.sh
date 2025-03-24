#!/bin/sh

echo "🚀 Stop Docker Compose..."
docker compose down

echo "🏗️  Remove all services..."
SERVICES=("config-server" "discovery-server" "gateway" "clinic-service" "doctor-service")  # Danh sách thư mục chứa các service

for SERVICE in "${SERVICES[@]}"; do
  echo "🔨 Removing $SERVICE..."
  docker rmi backend-$SERVICE
done

echo "✅ All services have been removed successfully!"

for SERVICE in "${SERVICES[@]}"; do
  echo "🔨 Building $SERVICE..."
  (cd "$SERVICE" && ./mvnw clean package -DskipTests)
  if [ $? -ne 0 ]; then
    echo "❌ Build failed for $SERVICE. Exiting..."
    exit 1
  fi
done

echo "✅ All services built successfully!"

docker compose up -d
