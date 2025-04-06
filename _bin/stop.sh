#!/bin/sh

echo "🏗️  Stopping Docker compose..."

cd "../backend" && docker compose down
