# Transaction Management System (TMS)

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
src/main/java/com/{user}/tms/
│
├── user/                  # User module
│    ├── controller/    
│    ├── service/
│    ├── repository/
│    ├── dto/
│    └── model/
├── account/               # Account module
├── transaction/           # Transaction module
├── ledger/                # Ledger module
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
- [x] **Account module**
  - [x] Create & manage bank accounts
  - [x] Account status management (active, frozen, closed)
  - [x] Balance tracking
- [x] **Transaction module**
  - [x] Transaction processing with ACID guarantees
  - [x] Idempotency key implementation
  - [ ] Transaction history & status tracking
  - [ ] Rollback on failure
- [ ] **Risk module**
  - [ ] Flag & block suspicious transactions

---

## Author

**Saidinesh Ponraj.**  
Computer Science (Co-op) · York University  
Aspiring Software Engineer — Banking & FinTech Systems

📧 [dineshsai841@gmail.com](mailto:dineshsai841@gmail.com)
