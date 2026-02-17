# 📚 Library Management System – Backend

<p>This is a Spring Boot based REST API for managing library operations such as adding books, issuing / returning books, etc. This Project follows a layered architecture with proper exception handling, validation and security.</p>

---

## 🚀 Features

- Add / Update / Search / Issue / Return / Delete Books
- Global Exception Handling
- User Management
- Request Validation
- Spring Security Configuration (Basic authentication)
- H2 In-Memory Database
- RESTful API Architecture

### ⚠️ Note

For simplicity and learning purposes, a separate `User` entity is not implemented in this project.  
Instead, two users (ADMIN and USER) are hardcoded in the security configuration.

Role-based access is enforced using Spring Security, and endpoints are pre-authorized based on user roles.

This approach was chosen to focus on backend architecture, security configuration, and REST API design.

## 🔑 System Overview & Access Control

This backend provides REST APIs for managing library operations such as books and users.

Two roles are configured using Spring Security:

- **ADMIN**
  - Add / Update / Delete Books
  - View all books
  - Issue & Return books (only admin can access issue / return book - when user give book to librarian then they verify book and then update it's status
    
- **USER**
  - Search book by book name or genre
  - view books
  - view book status (Available / Issued)
  - check book condition (New / Good / Old)

Role-based access is enforced using Spring Security with endpoint-level authorization.

---

## 🛠 Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- H2 Database
- Maven

---

## 📂 Project Structure

The project follows a layered architecture to maintain separation of concerns and clean code organization:

src/main/java/com/sb/BookManagement

- config        → Application and configuration classes
- controller    → REST controllers (API endpoints)
- service       → Business logic layer
- repositories → JPA repositories (data access layer)
- model         → Entity classes
- payload       → DTOs for request and response handling
- validation    → Custom validation logic
- exceptions    → Global exception handling
- security      → Spring Security configuration

Each layer has a clear responsibility, making the application modular, maintainable, and scalable.

---

## 👨‍💻 Author

**Puspa Raj Karna**  
B.Tech Computer Science Engineering  
Backend Developer | Spring Boot Enthusiast

---
