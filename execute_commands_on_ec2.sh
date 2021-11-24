#!/usr/bin/env bash

sudo kill -9 $(sudo lsof -t -i:80)
echo "Killed process running on port 80"

sudo java -jar app.jar > server.log &
echo "Starting server"
