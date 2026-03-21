# Movie Ticket Booking Platform

A high-concurrency, scalable backend system built with Spring Boot 3.4, designed to handle real-time seat reservations with zero double-booking risk.

# Architectural Highlights 
Pessimistic Concurrency Control: Uses SELECT FOR UPDATE (Pessimistic Write Locking) in MySQL to ensure that two users cannot book the same seat simultaneously.

Polyglot Persistence: * MySQL: Manages transactional data (Users, Bookings, ShowSeats) for ACID compliance.

MongoDB: Stores flexible movie metadata and cinema catalogs.

Stateless Security: Implements JWT-based authentication with role-based access control (RBAC).

Observability: Integrated structured logging with Slf4j and health monitoring via Spring Actuator.

Fault Tolerance: Implemented Resilience4j Circuit Breakers to handle database or service latencies.

# Tech Stack
Java 17 & Spring Boot 3.4

Databases: MySQL 8.0, MongoDB

Security: Spring Security + JWT

DevOps: Docker, Docker Compose

Documentation: Swagger UI / OpenAPI 3.0

# Getting Started (Run in 2 Minutes)
This project is fully containerized. You do not need MySQL or MongoDB installed locally.

Spin up the infrastructure:

```bash
   docker-compose up --build
Verify the build:
The application will be available at http://localhost:9090.

# API Documentation
Once the application is running, access the interactive Swagger UI to test the endpoints:
👉 http://localhost:9090/swagger-ui/index.html

Auth: /api/v1/user/register & /api/v1/user/login

Booking: /api/v1/booking (Requires Bearer Token)

# Testing the "Golden Path"
Register/Login to get your JWT.

Authorize in Swagger using the "Authorize" button (input: Bearer <your_token>).

Execute Booking: Call the POST endpoint.

Check Logs: Run docker logs -f movie_app_container to see the transaction lifecycle (Start -> Lock -> Success).
