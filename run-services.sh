#!/bin/sh

# Function to start a service
start_service() {
  SERVICE_NAME=$1
  echo "Starting ${SERVICE_NAME}..."
  (cd $SERVICE_NAME && mvn spring-boot:run) &
  SERVICE_PID=$!
  echo "${SERVICE_NAME} PID: ${SERVICE_PID}"
  sleep 10  # Wait for the service to start
  echo "${SERVICE_NAME} started."
  return $SERVICE_PID
}

# Start Service Registry
SERVICE_REGISTRY_PID=$(start_service "service-registry")

# Start Admin Service
ADMIN_SERVICE_PID=$(start_service "admin")

# Start Employee Service
EMPLOYEE_SERVICE_PID=$(start_service "employee")

# Start Gateway
GATEWAY_PID=$(start_service "gateway")

# Trap to kill all services on script exit
trap "kill $SERVICE_REGISTRY_PID $ADMIN_SERVICE_PID $EMPLOYEE_SERVICE_PID $GATEWAY_PID" EXIT

# Wait for all services to exit
wait
