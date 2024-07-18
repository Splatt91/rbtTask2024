#!/bin/bash

# Function to check if a port is in use and kill the process using it
kill_process_on_port() {
  local port=$1
  echo "Checking port: $port"

  # Check if the port is in use
  if lsof -i:"$port" > /dev/null; then
    # Find the PID of the process using the port
    pid=$(lsof -t -i:"$port")

    if [ -n "$pid" ]; then
      echo "Port $port is in use by process $pid"
      echo "Killing process $pid using port $port"
      kill -9 "$pid"
      echo "Process $pid killed"
    else
      echo "No process found using port $port"
    fi
  else
    echo "Port $port is not in use"
  fi
}

# Check if ports are provided as arguments
if [ $# -eq 0 ]; then
  echo "Usage: $0 <port1> <port2> ... <portN>"
  exit 1
fi

# Iterate over the provided ports and kill processes using them
for port in "$@"; do
  kill_process_on_port "$port"
done

echo "All specified ports have been processed."
