# Part Management System

This project is a Part Management System built using Spring Boot and MongoDB. It allows users to manage car parts including creating, updating, deleting, and retrieving parts.

## Table of Contents
- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [API Endpoints](#api-endpoints)
-[Plant UML Diagram] (#plant-ui-diagram)

## Getting Started

These instructions will help you set up and run the project on your local machine for development and testing purposes.

### Prerequisites

- Java 11 or higher
- Maven
- MongoDB

### Installation

1. Clone the repository
   ```sh
   git clone https://github.com/yourusername/part-management-system.git

cd part-management-system
mvn clean install

# application.properties
spring.data.mongodb.uri=mongodb://localhost:27017/partdb

mvn spring-boot:run

Endpoints:

POST /api/v1/parts
GET /api/v1/parts
POST /api/v1/parts/bulk
GET /api/v1/parts/search/{id}
GET /api/v1/parts/{ids}
GET /api/v1/parts/search
PATCH /api/v1/parts/{id}/decreaseStock?quantity={quantity}
PUT /api/v1/parts
GET /api/v1/parts/count
DELETE /api/v1/parts

@startuml
actor User

participant PartController
participant PartService
participant PartRepository
participant MongoDB

### Api-endpoints
User -> PartController: Create Part
PartController -> PartService: savePart(partRequestDto)
PartService -> PartRepository: save(part)
PartRepository -> MongoDB: insert part

User -> PartController: Retrieve All Parts
PartController -> PartService: getAllParts()
PartService -> PartRepository: findAll()
PartRepository -> MongoDB: query all parts
MongoDB -> PartRepository: return all parts
PartRepository -> PartService: return all parts
PartService -> PartController: return all parts
PartController -> User: return all parts

User -> PartController: Update Part
PartController -> PartService: updatePartsByFilter(updateRequests)
PartService -> PartRepository: updateOne(part)
PartRepository -> MongoDB: update part

User -> PartController: Delete Parts
PartController -> PartService: deleteParts(ids)
PartService -> PartRepository: deleteAllById(ids)
PartRepository -> MongoDB: delete parts

### Plant-ui-diagram

@enduml
@startuml
actor User

participant PartController
participant PartService
participant PartRepository
participant MongoDB

User -> PartController: Create Part
PartController -> PartService: savePart(partRequestDto)
PartService -> PartRepository: save(part)
PartRepository -> MongoDB: insert part

User -> PartController: Retrieve All Parts
PartController -> PartService: getAllParts()
PartService -> PartRepository: findAll()
PartRepository -> MongoDB: query all parts
MongoDB -> PartRepository: return all parts
PartRepository -> PartService: return all parts
PartService -> PartController: return all parts
PartController -> User: return all parts

User -> PartController: Update Part
PartController -> PartService: updatePartsByFilter(updateRequests)
PartService -> PartRepository: updateOne(part)
PartRepository -> MongoDB: update part

User -> PartController: Delete Parts
PartController -> PartService: deleteParts(ids)
PartService -> PartRepository: deleteAllById(ids)
PartRepository -> MongoDB: delete parts

@enduml