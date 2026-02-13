🚗 Dealership Microservices System

A microservices-based backend system designed as a practical exercise to demonstrate clean architecture, service decomposition, and inter-service communication, using a real-world dealership domain.

The project is built with Java & Spring Boot, uses REST APIs, includes initial gRPC examples, and is fully prepared for Kubernetes (K8s) deployment.

📚 Table of Contents

Overview

Architecture

Microservices

Places (Integrated into Offices)

Offices

Vehicles

Maintain (Repairs)

Sales

Customers

Inter-Service Communication

Kubernetes Deployment

Testing Status

Future Improvements

🧩 Overview

This repository contains a distributed backend system composed of multiple independent microservices.

The main goals of this project are:

Apply microservices architecture principles

Model realistic business rules

Demonstrate service-to-service communication

Show readiness for Kubernetes-based deployments

Provide a clean and extensible base for future improvements

⚠️ Note: Unit and integration tests are pending and will be added in future iterations.

🏗 Architecture

Microservices-based architecture

Independent deployments per service

No centralized service registry (no Eureka / Spring Cloud)

Service discovery handled by Kubernetes

REST for synchronous communication

Initial gRPC support (to be expanded)

Each microservice owns its domain logic and data, following clear responsibility boundaries.

🔧 Microservices
Places (Integrated into Offices)

Originally designed as a standalone microservice, Places was later integrated into the Offices service to simplify deployment and integration.

This module is intentionally reusable across other projects.

Entities

Countries

Provinces

Localities

📌 Design decision:
Buenos Aires (Capital Federal) is treated as a province instead of a locality to reduce domain complexity during development.

Offices

Manages dealership offices and delivery configurations.

Entities

Office – Dealership branches

TypeOffice – Office type (Central or Dealership)

DeliverySchedules – Delivery times:

Central → Dealership

Local delivery within the same dealership

Key Rules

The Central office always has id = 1

Creating or modifying another Central office is not allowed

Delivery times are globally configured (MVP simplification)

gRPC

This service includes a working gRPC example, serving as a foundation for future gRPC-based communication between services.

Vehicles

Manages all vehicle-related data.

Entities

ModelVehicles – Vehicle models (year and price)

TypeVehicles – Vehicle types and warranty years

VehicleStates

Available

Reserved

Sold

Delivered

Under Repair

Vehicles – Individual vehicles, including:

Current office location

Current state

Features

Vehicles can be queried by:

Office location

State

Model

Type

This allows sellers to identify which vehicles are available for sale in their office.

Maintain (Repairs)

Handles vehicle repairs.

Ideally this should be a fully independent microservice, but it was integrated for time constraints.

Entities

MechanicalRepairs

Business Rules

Only Delivered vehicles can be repaired

When a repair starts:

Vehicle state → Under Repair

When a repair ends:

Vehicle state → Delivered

Warranty Validation

Warranty years are copied into the Sale at purchase time

This preserves historical consistency if warranty rules change

Warranty validation requires querying the Sales service

Sales

Manages dealership sales and contains the most complex business logic.

Entities

Customers

Employees

SaleStates

Pending

Completed

Canceled

Sales

Core Logic

When creating a sale:

Validates vehicle and office existence

Calculates delivery date:

Local delivery if vehicle belongs to the seller’s office

Central → Dealership + local delivery otherwise

Retrieves warranty data from Vehicles

Updates vehicle state to Reserved

When finalizing a sale:

Vehicle state → Sold

This service interacts heavily with Offices and Vehicles.

Customers

Manages customer information.

Entities

Customers

Provides standard CRUD operations.

🔗 Inter-Service Communication

REST APIs for most service interactions

gRPC:

Implemented as a working example in Offices

Additional gRPC integrations will be added in other services

☸ Kubernetes Deployment

The repository includes a dedicated k8s/ directory containing:

Deployment manifests per microservice

Service definitions for internal communication

Environment configuration via YAML

This allows the full system to be deployed into a Kubernetes cluster without external infrastructure dependencies.

✔ No Eureka
✔ No Spring Cloud Server
✔ Kubernetes-native service discovery

🧪 Testing Status

🚧 Pending:

Unit tests

Integration tests

These will be added in future iterations to improve robustness and coverage.

🚀 Future Improvements

Add unit and integration tests

Expand gRPC usage across microservices

Improve validation rules (e.g. delete constraints)

Extract Maintain into an independent microservice

Add observability (metrics, tracing, logging)

Improve resilience (timeouts, retries)