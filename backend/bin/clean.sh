#!/bin/sh

echo "🚀 Stop Docker Compose and Remove volume..."
docker compose down

echo "🏗️  Remove all services..."
SERVICES=($(cat services.txt | tr -d '\r'))

for SERVICE in "${SERVICES[@]}"; do
  echo "🔨 Removing $SERVICE..."
  docker rmi backend-$SERVICE -f
done

echo "✅ All services have been removed successfully!"
