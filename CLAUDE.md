# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

A microservices-based car dealership backend built with Java 17 and Spring Boot 3.5.0. Each microservice is an independent Maven project with its own `pom.xml`, database, and deployment configuration.

## Microservices

| Service | Directory | REST Port | gRPC Port |
|---------|-----------|-----------|-----------|
| offices | `offices/` | 8082 | 9092 |
| vehicles | `Vehicles/` | 8083 | — (client only) |
| sales | `Sales/` | 8084 | — |
| customers | `customers/` | 8085 | 9095 |
| maintain | `maintain/` | 8086 | — |

All services use an **in-memory H2 database** seeded via `src/main/resources/data.sql`. There is no shared parent POM — each service is built independently.

## Build & Run Commands

All commands must be run from inside the specific service directory (e.g., `cd offices`).

```bash
# Build (skip tests)
./mvnw clean package -DskipTests

# Run locally
./mvnw spring-boot:run

# Run tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=ClassName

# Docker (dev mode with live reload)
docker compose -f docker-compose.dev.yaml up --build

# Docker (production)
docker compose -f docker-compose.prod.yaml up --build
```

Protobuf/gRPC Java stubs are generated automatically during `mvn compile` via the `protobuf-maven-plugin`. The `.proto` files live in `src/main/proto/`.

## Architecture

### Layered Package Structure (per service)

Each service follows the same layered architecture under `src/main/java/org/emanuel/<service>/`:

- `domain/` — Domain entities, enums, and domain exceptions
- `application/` — Service interfaces (`IXxxService`)
- `infra/rest/` — REST controllers, DTOs, mappers, and REST exception handlers
- `infra/grpc/` — gRPC service implementations and gRPC exception handlers
- `infra/integration/` — Feign clients for calling other microservices (REST)

### Inter-Service Communication

- **REST (Feign)**: Used for most cross-service calls. Feign client interfaces are defined in `infra/integration/<target-service>/`. URLs are configured in `application.properties` (e.g., `offices.service.url`, `vehicles.service.url`).
- **gRPC**: Currently implemented between `offices` (server) and `vehicles` (client). The shared `.proto` contract is duplicated in both services under `src/main/proto/office.proto` with package `org.emanuel.contracts.proto.offices`. The `customers` service also exposes a gRPC server (`customer.proto`, port 9095).

### Key Business Rules

- **Offices**: The Central office always has `id = 1`. Only one Central office is allowed.
- **Vehicles**: States are an enum — `Available`, `Reserved`, `Sold`, `Delivered`, `UnderRepair`. State transitions are driven by Sales and Maintain services.
- **Sales**: Most complex service. On sale creation: validates vehicle/office, calculates delivery date (local vs. Central→Dealership), sets vehicle to `Reserved`. On finalization: sets vehicle to `Sold`.
- **Maintain**: Only `Delivered` vehicles can have repairs. Repair start → `UnderRepair`; repair end → `Delivered`. Warranty validation queries the Sales service.

### Swagger / OpenAPI

Each service exposes Swagger UI at `http://localhost:<port>/swagger-ui.html` via `springdoc-openapi-starter-webmvc-ui:2.8.9`.

### Kubernetes

K8s manifests are in `k8s/<service>/`. Each file contains a `Deployment`, `Service` (ClusterIP), and `Ingress`. Services communicate by Kubernetes DNS name (no Eureka/Spring Cloud). Images must be built locally (`imagePullPolicy: IfNotPresent`).

## Technology Stack

- Java 17, Spring Boot 3.5.0
- Spring Data JPA + H2 (in-memory)
- Spring Cloud OpenFeign (inter-service REST)
- gRPC via `net.devh:grpc-spring-boot-starter:3.1.0.RELEASE` + `io.grpc:grpc-bom:1.63.0`
- Protobuf 3.25.1 (stubs generated at compile time)
- Lombok 1.18.30
- Springdoc OpenAPI 2.8.9
