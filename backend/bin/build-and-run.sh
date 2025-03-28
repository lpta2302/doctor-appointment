#!/bin/sh

echo "🏗️  Building all services..."
SERVICES=($(cat services.txt))

for SERVICE in "${SERVICES[@]}"; do
  echo "🔨 Building $SERVICE..."
  (cd "../$SERVICE" && ./mvnw clean package -DskipTests)
  if [ $? -ne 0 ]; then
    echo "❌ Build failed for $SERVICE. Exiting..."
    exit 1
  fi
done

echo "✅ All services built successfully!"

echo "🚀 Starting Docker Compose..."
docker compose up -d
