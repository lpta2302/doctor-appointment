#!/bin/sh

echo "🚀 Stop Docker Compose and Remove volume..."
docker compose down -v

echo "🏗️  Remove all services..."
SERVICES=($(cat services.txt))

for SERVICE in "${SERVICES[@]}"; do
  echo "🔨 Removing $SERVICE..."
  docker rmi backend-$SERVICE
done

echo "✅ All services have been removed successfully!"
