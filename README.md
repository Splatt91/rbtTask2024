
## Using the `start_services.sh` Script

The `start_services.sh` script starts the microservices in the correct order, ensuring that any processes using specified ports are killed before starting the services. The script also includes a shutdown function to clean up and stop all services when it exits.

### How to Use

1. **Make the scripts executable**:

    ```bash
    chmod +x kill_ports.sh
    chmod +x start_services.sh
    ```

2. **Run the `start_services.sh` script**:

    ```bash
    ./start_services.sh
    ```

### Script Explanation

- **`kill_ports.sh`**:
    - This script checks for processes using specified ports and kills them.
    - Usage within `start_services.sh`.

- **`start_services.sh`**:
    - This script first kills processes using specified ports by calling `kill_ports.sh`.
    - It starts the `service-registry`, waits for it to be up, then starts Kafka using Docker Compose.
    - It proceeds to start `admin-service`, `employee-service`, and `gateway`.
    - It includes a `shutdown` function to stop all services when the script exits.

### Used Ports

- **`service-registry`**: Uses default Spring Boot port `8761`.
- **`admin-service`**: Uses port `8081`.
- **`employee-service`**: Uses port `8082`.
- **`gateway`**: Uses port `9000`.
- **Kafka**: Uses default port `9092`.
- **Kafka UI**: Typically accessed on port `9000` (ensure no conflicts).

## Microservices overview

Postman collection called "Vacation Tracker.postman_collection.json" is inside rbtTask2024 folder

### admin-service

- Uses api key protection
  - api key is hardcoded to value: secret-api-key

- Use case
  1. Import employee profiles
  2. Import total vacation days per employee per year
  3. Import records of used vacation days per employee
      - This API call triggers update in databse for total vacation days

### employee-service

- Uses basic authentication
  - You can authenticate by using one of the users from the provided sample file

- Employees can add a new record of used vacation days.
- If everything has been imported via admin-service Employee can
  - search/see the number of total, used and available vacation days per year
  - search/list records of used vacation days for a specified time period(specified from and to dates).