# 🧠 BrainFlow - Backend API

![Spring Boot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white)

The core backend service for **BrainFlow**, a full-stack ticketing and management platform. This RESTful API is built with Spring Boot and designed to handle robust data operations, user authentication, and business logic for support environments.

## ✨ Core Features
*   **Secure Architecture:** Implemented Spring Security with custom filter configurations and CORS management.
*   **Ticket Lifecycle Management:** Endpoints for creating, updating, assigning, and resolving tickets with varying priority levels (Low, Medium, High, Critical).
*   **Data Validation & Transfer:** Strict separation of concerns using Data Transfer Objects (DTOs) to secure and streamline API requests/responses.
*   **Automated Metrics:** Backend logic to support accurate Service Level Agreement (SLA) tracking and performance indicators.

## 🛠️ Technology Stack
*   **Framework:** Spring Boot (Java)
*   **Security:** Spring Security (RBAC)
*   **Data Access:** Spring Data JPA / Hibernate
*   **Build Tool:** Maven

## 🚀 Getting Started

### Prerequisites
*   Java 17 or higher
*   Maven installed
*   Your preferred database running (e.g., MySQL, PostgreSQL, or Oracle)

### Installation & Setup
1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/SoumiaRachidi/BrainFlow_Backend.git](https://github.com/SoumiaRachidi/BrainFlow_Backend.git)
    cd BrainFlow_Backend
    ```
2.  **Configure the database:**
    Open `src/main/resources/application.properties` and update your database credentials:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/brainflow_db
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    ```
3.  **Build and run the application:**
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```
    The server will start on `http://localhost:8080`.

## 👨‍💻 Author
*   **Souma** - [GitHub Profile](https://github.com/SoumiaRachidi)
