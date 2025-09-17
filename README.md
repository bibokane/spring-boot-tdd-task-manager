# TDD Project - Task Management System

A Spring Boot application demonstrating Test-Driven Development (TDD) principles with a simple task management system and calculator functionality.

## 🚀 Features

- **Task Management API**: Complete CRUD operations for tasks
- **Calculator Module**: Basic calculator with division functionality
- **Test-Driven Development**: Comprehensive test coverage using JUnit 5
- **RESTful API**: Clean REST endpoints for task operations
- **Data Validation**: Input validation using Jakarta Validation
- **Exception Handling**: Custom exception handling for better error management

## 🛠️ Tech Stack

- **Java 21**
- **Spring Boot 3.5.5**
- **Spring Data JPA**
- **H2 Database** (In-memory)
- **Lombok** (for reducing boilerplate code)
- **JUnit 5** (for testing)
- **Maven** (for dependency management)

## 📋 API Endpoints

### Task Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/tasks/` | Get all tasks |
| POST | `/tasks` | Create a new task |
| GET | `/tasks/{id}` | Get task by ID |
| PUT | `/tasks/{id}` | Update task by ID |
| DELETE | `/tasks/{id}` | Delete task by ID |

### Example Task Object

```json
{
  "title": "Complete TDD project",
  "status": "In Progress"
}
```

## 🏃‍♂️ Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6 or higher

### Installation

1. **Clone the repository**
   ```bash
   git clone <your-repository-url>
   cd tddProject
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - API Base URL: `http://localhost:8080`
   - H2 Console: `http://localhost:8080/h2-console` (if enabled)

## 🧪 Running Tests

Execute all tests:
```bash
mvn test
```

Run specific test classes:
```bash
mvn test -Dtest=CalculatorTest
mvn test -Dtest=TaskControllerTest
```

## 📁 Project Structure

```
src/
├── main/java/org/example/tddproject/
│   ├── controller/          # REST controllers
│   ├── service/            # Business logic
│   ├── repository/         # Data access layer
│   ├── model/              # Entity models
│   ├── exception/          # Custom exceptions
│   ├── firstTDDTest/       # Calculator implementation
│   └── TddProjectApplication.java
└── test/java/org/example/tddproject/
    ├── controller/         # Controller tests
    ├── service/           # Service tests
    ├── repository/        # Repository tests
    └── firstTDDTest/      # Calculator tests
```

## 🎯 TDD Examples

### Calculator Division Test

```java
@Test
void testDivideTwoNumbers() {
    // Arrange
    Calculator calculator = new Calculator();
    
    // Act
    double result = calculator.divide(6, 2);
    
    // Assert
    assertEquals(3.0, result);
}
```

### Task Creation Test

```java
@Test
void testCreateTask() {
    // Arrange
    Task task = new Task("Test Task", "Pending");
    
    // Act
    Task createdTask = taskService.createTask(task);
    
    // Assert
    assertNotNull(createdTask.getId());
    assertEquals("Test Task", createdTask.getTitle());
}
```

## 🔧 Configuration

The application uses an in-memory H2 database by default. Database configuration can be found in `src/main/resources/application.properties`.

## 📝 Development Notes

- This project follows TDD principles: Tests are written before implementation
- All business logic is thoroughly tested with unit tests
- The calculator module demonstrates basic TDD with edge case handling (division by zero)
- The task management system shows TDD applied to a Spring Boot REST API

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Write tests for your new feature
4. Implement the feature
5. Ensure all tests pass
6. Commit your changes (`git commit -m 'Add some amazing feature'`)
7. Push to the branch (`git push origin feature/amazing-feature`)
8. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

Created as part of a TDD learning project.

---

**Happy Testing! 🧪✨**
