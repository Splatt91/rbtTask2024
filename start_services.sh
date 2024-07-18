#!/bin/bash

# Path to the kill_ports.sh script
KILL_PORTS_SCRIPT="./kill_ports.sh"

# Ports to be checked and killed
PORTS=(2181 5433 5434 8080 8081 8082 8761 8090 9000 9093)

# Save the current folder path
CURRENT_FOLDER=$(pwd)

# Call the kill_ports.sh script with the specified ports
echo "Killing processes using ports: ${PORTS[*]}"
$KILL_PORTS_SCRIPT "${PORTS[@]}"

# Function to check if service is up
wait_for_service() {
  local url=$1
  local name=$2
  echo "Waiting for $name to start..."
  until curl --output /dev/null --silent --head --fail "$url"; do
    printf '.'
    sleep 5
  done
  echo "$name is up!"
}


# Function to clean up and stop all running services
shutdown() {
  echo "Shutting down services..."

  # Find and kill processes using pgrep
  for service in "service-registry" "admin" "employee" "gateway"; do
    pids=$(pgrep -f "$service")
    if [ -n "$pids" ]; then
      echo "Stopping $service with PIDs $pids..."
      kill -9 $pids
      echo "$service stopped."
    else
      echo "No running process found for $service."
    fi
  done

  echo "Docker cleanup..."
  pushd "$CURRENT_FOLDER/admin" || exit
  docker compose down -v
  popd || exit

  pushd "$CURRENT_FOLDER/employee" || exit
  docker compose down -v
  popd || exit

  pushd "$CURRENT_FOLDER/kafka" || exit
  docker compose down -v
  popd || exit

  echo "All services stopped."
}

# Trap exit signal and call the shutdown function
trap shutdown EXIT

# Start service-registry
echo "Starting service-registry..."
pushd ./service-registry || exit
mvn spring-boot:run &
SERVICE_REGISTRY_PID=$!
wait_for_service "http://localhost:8761" "service-registry"
popd || exit
pwd

# Start Kafka using Docker Compose
echo "Starting Kafka..."
pushd ./kafka || exit
docker-compose up -d
popd || exit

# Start admin-service
echo "Starting admin-service..."
pushd ./admin || exit
mvn spring-boot:run &
ADMIN_SERVICE_PID=$!
popd || exit

# Start employee-service
echo "Starting employee-service..."
pushd ./employee || exit
mvn spring-boot:run &
EMPLOYEE_SERVICE_PID=$!
popd || exit

# Start gateway
echo "Starting gateway..."
pushd ./gateway || exit
mvn spring-boot:run &
GATEWAY_PID=$!
popd || exit

# Wait for all services to finish
wait $SERVICE_REGISTRY_PID
wait $ADMIN_SERVICE_PID
wait $EMPLOYEE_SERVICE_PID
wait $GATEWAY_PID

echo "All services started successfully."
