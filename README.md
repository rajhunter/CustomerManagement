# CustomerManagement

_A clean reference implementation built with **Spring Boot 3.5.5** and **Java 17**. It includes CRUD endpoints, validation, an H2 in-memory database, OpenAPI docs (Swagger UI), and tests._

---

## ✨ Features
- RESTful CRUD for Customers
- Request validation (e.g., email format, required fields)
- H2 in-memory database with console
- Auto-generated OpenAPI 3 docs via Springdoc / Swagger UI
- Layered architecture with service + repository
- Unit & MVC tests

---

## 🚀 Quick Start
```bash
mvn clean install -U 
mvn spring-boot:run
                 OR
mvn clean install -U && mvn spring-boot:run

```

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **H2 Console:** http://localhost:8080/h2-console
    - **JDBC URL:** `jdbc:h2:mem:customersdb`

> Maven 3.9+ and JDK 17 required.

---

## 🧪 Running Tests
```bash
mvn clean test
```

---

## 📦 Build a Jar
```bash
mvn -DskipTests package
java -jar target/customer-management-api-*.jar
```

---

## 🔌 Endpoints Overview
The service exposes the following endpoints (see full OpenAPI spec below):

- `POST /api/v1/customers` – Create a customer
- `PUT /api/v1/customers/{uuid}` – Update an existing customer
- `GET /api/v1/getCustomer/{uuid}` – Get a customer by UUID
- `GET /api/v1/getCustomerByName/{customerName}` – List customers by name
- `GET /api/v1/getCustomerByEmailId/{emailId}` – Find customers by email
- `GET /api/v1/getCustomerAnnualSpendsByEmail/{emailId}` – Annual spends by email
- `GET /api/v1/getCustomerAnnualSpends/{searchType}` – Annual spends by search type
- `DELETE /api/v1/customers/{id}` – Delete a customer by UUID

### Example cURL
```bash
# Create
curl -X POST http://localhost:8080/api/v1/customers   -H 'Content-Type: application/json'   -d '{
        "customerName": "Alice",
        "emailId": "alice@example.com",
        "annualSpend": 1200.50
      }'

# Get by ID
curl http://localhost:8080/api/v1/getCustomer/{uuid}

# Delete
curl -X DELETE http://localhost:8080/api/v1/customers/{uuid}
```

---

## 🗂️ Data Models (Schemas)
High-level shapes (see OpenAPI for full detail):

- **CustomerRequest**: `customerName` (string, required), `emailId` (string, required), `annualSpend` (number)
- **CustomerResponse**: `uuid` (uuid), `customerName`, `emailId`, `annualSpend`, `lastPurchaseDate` (date), `teirType` (enum: SILVER|GOLD|PLATINUM)
- **Customer**: Same as response without `teirType`
- **Wrapped responses**: `CustomApiResponseCustomerResponse`, `CustomApiResponseListCustomer`, `CustomApiResponseString`, `ApiResponse`

> Note: The field is spelled `teirType` (not `tierType`) to match the API schema.

---

## 📘 OpenAPI 3.0.1 Specification
(See README canvas for full YAML spec)

---

## 🧰 H2 Console
- Navigate to `http://localhost:8080/h2-console`
- Driver Class: `org.h2.Driver`
- JDBC URL: `jdbc:h2:mem:customersdb`
- User Name: `sa` (default) | Password: _empty_

> H2 runs in-memory and resets on application restart.

---