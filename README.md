# Transaction Management System (TMS)

> ⚠️ **Status: In Development**

A high-fidelity system designed to simulate real-world banking operations — secure, reliable, and consistent handling of money transfers. TMS supports concurrent transactions, prevents duplicates, and maintains integrity under failure scenarios, demonstrating production-grade financial system behavior.

---

## Features & Scope

- **Account Management** — Create and manage user accounts with balance tracking
- **Atomic Transfers** — Ensures consistency via database transactions with rollback on failure
- **Concurrency Safety** — Handles multiple concurrent transactions using optimistic/pessimistic locking
- **Idempotency** — Prevents duplicate transactions through unique request ID handling
- **Audit & Logging** — Append only audit trail for all necessary events.
- **Deployment Ready** — Dockerized for local or cloud deployment, with logging and health checks
- **Tested & Documented** — Unit and integration tests included; Swagger UI for API documentation

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot |
| Security | Spring Security + JWT |
| Build Tool | Maven |
| Database | PostgreSQL |
| Cache | Redis |
| API Testing | Postman |
| Frontend *(future)* | React |

---

## Project Structure

```
src/main/java/com/dinesh/tms/
│
├── user/                  # User module
│    ├── controller/    
│    ├── service/
│    ├── repository/
│    ├── dto/
│    └── model/
├── account/               # Account module
├── transaction/           # Transaction module
├── common/                # Shared entities, DTOs, and utilities
│    └── exception/        # Custom exceptions
├── risk/                  # Risk rule engine & scoring
├── audit/                 # Audit & logging module
└── config/                # App configuration
```

---

## Roadmap

- [x] **Project scaffolding & architecture design**
- [x] **User module**
  - [x] User creation
  - [x] User validation
- [ ] **Account module**
  - [x] Create & manage bank accounts
  - [x] Account status management (active, frozen, closed)
  - [ ] Balance tracking
- [ ] **Transaction module**
  - [ ] Transaction processing with ACID guarantees
  - [ ] Idempotency key implementation
  - [ ] Transaction history & status tracking
  - [ ] Rollback on failure
- [ ] **Risk module**
  - [ ] Rule engine implementation
  - [ ] Risk scoring per transaction
  - [ ] Flag & block suspicious transactions
- [ ] **Audit & Logging module**
  - [ ] Audit trail for all account & transaction events
  - [ ] Immutable audit log (append-only)
  - [ ] Log authentication events (login, failed attempts, token refresh)
  - [ ] Structured logging with correlation IDs
  - [ ] Log shipping & monitoring integration *(e.g. ELK Stack / Grafana)*
- [ ] **Infrastructure**
  - [ ] Redis caching layer
  - [ ] Docker deployment configuration
  - [ ] Logging & health checks
- [ ] **Testing & Docs**
  - [ ] Unit tests
  - [ ] Integration tests
  - [ ] Swagger UI documentation
  - [ ] Load testing using J meter
- [ ] **Frontend** *(future scope)*
  - [ ] React admin dashboard
---

## Author

**Saidinesh Ponraj.**  
Computer Science (Co-op) · York University  
Aspiring Software Engineer — Banking & FinTech Systems

📧 [dineshsai841@gmail.com](mailto:dineshsai841@gmail.com)
