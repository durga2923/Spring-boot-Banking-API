# Local Development Setup Guide

This guide explains how to run the Spring Boot Banking API microservices locally for development.

## Prerequisites

- Java 11+
- Maven 3.6+
- Docker & Docker Compose

## Quick Start

### 1. Start Infrastructure Services

Start MySQL, RabbitMQ, and MongoDB using Docker Compose:

```bash
docker-compose -f docker-compose-dev.yml up -d
```

**Services started:**
- MySQL: `localhost:3306` (root/test1234)
- RabbitMQ: `localhost:5672` (guest/guest), Management UI: `localhost:15672`
- MongoDB: `localhost:27017` (admin/password)

**Verify containers are running:**
```bash
docker ps
```

### 2. Start Core Services (in order)

#### Step 1: Spring Cloud Config Server (port 8888)
```bash
cd Spring-cloud-config-server
./mvnw spring-boot:run
```

Wait until you see: `Started SpringCloudConfigServerApplication`

**Verify:** 
```bash
curl http://localhost:8888/transaction/dev
```
Should return JSON with `propertySources` containing config values.

#### Step 2: Eureka Naming Server (port 8761)
```bash
cd Eureka-naming-server
./mvnw spring-boot:run
```

Wait until you see: `Started EurekaNamingServerApplication`

**Access Eureka Dashboard:** http://localhost:8761

### 3. Start Business Services (order doesn't matter)

Each service can be started in a separate terminal:

#### Customer Service (port 8081)
```bash
cd Customer
./mvnw spring-boot:run
```

#### Employee Service (port 8082)
```bash
cd Employee
./mvnw spring-boot:run
```

#### Loan Service (port 8083)
```bash
cd Loan
./mvnw spring-boot:run
```

#### Transaction Service (port 8084)
```bash
cd Transaction
./mvnw spring-boot:run
```

#### Issues Service (port 8085)
```bash
cd Issues
./mvnw spring-boot:run
```

### 4. Start API Gateway (port 8765)

After all business services are running:

```bash
cd Api-gateway
./mvnw spring-boot:run
```

## Service Ports Summary

| Service                    | Port  | Description                          |
|----------------------------|-------|--------------------------------------|
| Spring Cloud Config Server | 8888  | Centralized configuration            |
| Eureka Naming Server       | 8761  | Service discovery                    |
| API Gateway                | 8765  | Gateway/routing                      |
| Customer Service           | 8081  | Customer management                  |
| Employee Service           | 8082  | Employee management                  |
| Loan Service               | 8083  | Loan management                      |
| Transaction Service        | 8084  | Transaction management               |
| Issues Service             | 8085  | Issue tracking                       |
| MySQL                      | 3306  | Database                             |
| RabbitMQ                   | 5672  | Message queue                        |
| RabbitMQ Management        | 15672 | RabbitMQ admin UI (guest/guest)      |
| MongoDB                    | 27017 | NoSQL database (Issues service)      |

## Security Configuration

For local development, OAuth2/JWT security is **disabled by default** via the configuration property:

```properties
app.security.enabled=false
```

This is set in `git-localconfig-repo/application-dev.properties`.

### To Enable Security

1. Set `app.security.enabled=true` in `application-dev.properties`
2. Start Keycloak on port 9090
3. Configure the realm `Springboot-bank-microservices`
4. Restart all services

## Database Configuration

### MySQL Databases Created Automatically

- `Transaction` - Transaction service
- `customer-dev` - Customer service
- `Loan` - Loan service
- `employee-dev` - Employee service

Credentials: `root` / `test1234`

### MongoDB Database

- Database: `issues`
- Credentials: `admin` / `password`

## Flyway Migrations

Services use Flyway for database migrations. Migration scripts are in:
- `Transaction/src/main/resources/db/migration/`
- `Customer/src/main/resources/db/migration/`
- `Loan/src/main/resources/db/migration/`
- `Employee/src/main/resources/db/migration/`

Migrations run automatically on service startup.

## Troubleshooting

### Config Server Returns Empty propertySources

**Problem:** `curl http://localhost:8888/transaction/dev` returns `"propertySources":[]`

**Solution:** 
- Verify `Spring-cloud-config-server/src/main/resources/application.properties` has the correct path to `git-localconfig-repo`
- Restart the config server

### Flyway Connection Errors

**Problem:** `Communications link failure` when starting services

**Solution:**
- Verify MySQL container is running: `docker ps | grep mysql`
- Check MySQL logs: `docker logs banking-mysql`
- Ensure port 3306 is not in use by another service

### Service Won't Register with Eureka

**Problem:** Services don't appear in Eureka dashboard

**Solution:**
- Ensure Eureka is running on port 8761
- Check service logs for connection errors
- Verify `eureka.client.enabled=true` in config

### Port Already in Use

**Problem:** `Port 8888 is already in use`

**Solution:**
```bash
# Find the process
lsof -i :8888

# Kill the process (replace PID with actual process ID)
kill <PID>
```

## Stopping Services

### Stop Spring Boot Services
Press `Ctrl+C` in each terminal running a service.

### Stop Docker Containers
```bash
docker-compose -f docker-compose-dev.yml down
```

To also remove volumes (deletes all data):
```bash
docker-compose -f docker-compose-dev.yml down -v
```

## API Documentation

Each service exposes Swagger UI at:
- http://localhost:8081/swagger-ui/index.html (Customer)
- http://localhost:8082/swagger-ui/index.html (Employee)
- http://localhost:8083/swagger-ui/index.html (Loan)
- http://localhost:8084/swagger-ui/index.html (Transaction)
- http://localhost:8085/swagger-ui/index.html (Issues)

## Development Tips

### Hot Reload

Services use Spring Boot DevTools for automatic restart on code changes.

### Running Individual Tests

```bash
cd <service-directory>
./mvnw test
```

### Building Without Tests

```bash
./mvnw clean package -DskipTests
```

### Viewing RabbitMQ Messages

Access RabbitMQ Management UI at http://localhost:15672 (guest/guest)

## VS Code / IDE Integration

To run services from VS Code:
1. Open the service folder
2. Use the Spring Boot Dashboard extension
3. Click "Run" on the application class

Alternatively, use the Java debugger to run/debug the main application class.
