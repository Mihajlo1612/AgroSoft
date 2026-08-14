# AgroSoft

> A full-stack **farm management** application built with **Java 21** and **Spring Boot**, exposing a secure REST API backed by PostgreSQL, JWT authentication, versioned Flyway migrations, and OpenAPI documentation.

![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-6DB33F?logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-4169E1?logo=postgresql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?logo=apachemaven&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?logo=docker&logoColor=white)

---

## About

AgroSoft is a farm management application built independently with Spring Boot. It exposes a well-structured REST API secured with Spring Security and JSON Web Tokens, persists data in a PostgreSQL database managed through versioned Flyway migrations, and ships interactive API documentation via Swagger / OpenAPI. Server-rendered views are provided with Thymeleaf, and the database runs in a containerized environment via Docker Compose.

---

## Features

- **Secure REST API** — Spring MVC endpoints protected with Spring Security.
- **JWT authentication** — Stateless auth using JJWT (access tokens), with **BCrypt** password hashing.
- **DTO pattern & IDOR protection** — Request/response DTOs decouple the API from entities and guard against insecure direct object references.
- **Relational data model** — PostgreSQL schema evolved through versioned **Flyway** migrations.
- **Input validation** — Bean Validation (`spring-boot-starter-validation`) on incoming requests.
- **API documentation** — Interactive Swagger UI generated with springdoc-openapi.
- **Server-rendered views** — Thymeleaf templates for the web layer.
- **Containerized database** — PostgreSQL provisioned via Docker Compose for reproducible local setup.

---

## Tech Stack

| Layer                | Technology                                        |
| -------------------- | ------------------------------------------------- |
| **Language**         | Java 21                                            |
| **Framework**        | Spring Boot 4.1 (Spring MVC)                       |
| **Security**         | Spring Security + JJWT 0.12 (JWT), BCrypt          |
| **Persistence**      | Spring Data JPA / Hibernate                        |
| **Database**         | PostgreSQL                                         |
| **Migrations**       | Flyway (PostgreSQL)                                |
| **Validation**       | Jakarta Bean Validation                            |
| **API Docs**         | springdoc-openapi (Swagger UI)                     |
| **Views**            | Thymeleaf                                          |
| **Build**            | Maven (Maven Wrapper included)                     |
| **Containerization** | Docker Compose (PostgreSQL)                        |

---

## Project Structure

```
AgroSoft/
├── src/
│   ├── main/
│   │   ├── java/com/agrosoft/     # Controllers, services, repositories, security, DTOs, entities
│   │   └── resources/
│   │       ├── db/migration/      # Flyway versioned migrations (V1__..., V2__...)
│   │       ├── templates/         # Thymeleaf templates
│   │       └── application.*       # Configuration
│   └── test/                      # Unit & web-layer tests
├── docker-compose.yml             # PostgreSQL service
├── pom.xml
└── mvnw / mvnw.cmd                # Maven wrapper
```

---

## Getting Started

### Requirements

- JDK 21
- Docker & Docker Compose (for PostgreSQL)
- Maven (or use the bundled `./mvnw` wrapper)

### 1. Start the database

```bash
docker compose up -d
```

### 2. Configure environment

Set the database connection and JWT secret (via environment variables or `application.properties`), for example:

```
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/agrosoft
SPRING_DATASOURCE_USERNAME=agrosoft
SPRING_DATASOURCE_PASSWORD=********
JWT_SECRET=your-strong-secret
```

> Never commit real secrets — keep them in environment variables or an untracked local config.

### 3. Run the application

```bash
# macOS/Linux
./mvnw spring-boot:run

# Windows
.\mvnw.cmd spring-boot:run
```

Flyway applies migrations automatically on startup.

### 4. Explore the API

Once running, open the Swagger UI (default):

```
http://localhost:8080/swagger-ui.html
```

### Build & Test

```bash
./mvnw clean package     # build a runnable JAR
./mvnw test              # run tests
```

---

## Security Notes

- **Authentication:** clients obtain a JWT on login; the token is validated on each request by a security filter.
- **Passwords:** stored as BCrypt hashes, never in plain text.
- **IDOR protection:** endpoints verify that the authenticated user owns the requested resource before returning it.
- **DTOs:** entities are never exposed directly; dedicated DTOs control the API surface.

---

## License

No license specified yet. Add a `LICENSE` file if you intend to open-source this project.
