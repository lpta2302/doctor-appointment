#!/bin/sh


FILES=("run.sh", "build-and-run.sh", "rebuild.sh", "rebuild-and-run.sh", "stop.sh", "clean.sh")

for FILE in "${FILES[@]}"; do
  chmod +x $FILE
done