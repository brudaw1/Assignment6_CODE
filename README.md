# SOEN 333 Software Testing - Assignment 6

## Overview

# Barnes & Noble Shopping System

![Build Status](https://github.com/brudaw1/Assignment_6/actions/workflows/SE333_CI.yml/badge.svg)

## Overview
This project is a Java-based implementation of a **Barnes & Noble** book purchasing system. It includes:
- A **Book Database** for storing book information.
- A **Purchase Process** to handle book purchases.
- A **Shopping System** to integrate different operations.

---

## Assignment 6 - Part 1
This assignment focuses on testing the `BarnesAndNoble` project using:
- **Specification-based Testing**: Tests based on provided requirements.
- **Structural-based Testing**: Tests based on internal code structure.

### 🛠 Testing Tools
- **JUnit 5** for writing tests.
- **Mockito** for mocking dependencies (if needed).
- **AssertJ** for fluent assertions.

---

## Assignment 6 - Part 2
This part automates **testing and static analysis** using **GitHub Actions**.

### Continuous Integration (CI) Workflow
Each push to the `main` branch triggers the following automated steps:
1. **Static Analysis (Checkstyle)**: Ensures code quality and maintains style consistency.
2. **Unit Tests Execution (JUnit & Mockito)**: Runs all test cases.
3. **Code Coverage Analysis (Jacoco)**: Generates a test coverage report.

### Automated Reports
GitHub Actions **automatically generates and stores** the following artifacts:
- **Checkstyle Report** (`checkstyle-result.xml`) – Code style analysis.
- **Jacoco Code Coverage Report** (`jacoco-report`) – Test coverage details.

---

## Assignment 6 - Part 3
This part expands the testing suite by writing larger tests—including both **unit tests** and **integration tests**—for the Amazon package.

### Testing Objectives
- **Integration Testing**: Verifies interactions between components (e.g., `Amazon`, `ShoppingCartAdaptor`, and the database). The database is reset before each test to ensure a clean state.
- **Unit Testing**: Isolates individual components using test doubles (mocks or stubs) to validate internal logic and behavior.

### Test Organization
- **AmazonUnitTest.java**: Contains unit tests for the Amazon package. Tests are organized as:
    - **Specification-based tests**: Validating expected behaviors (e.g., correct price calculation when the cart is empty).
    - **Structural-based tests**: Confirming internal interactions (e.g., ensuring `PriceRule.priceToAggregate` is invoked).
- **AmazonIntegrationTest.java**: Contains integration tests that involve real database operations and interactions between system components.

### Annotations
Both test classes use the `@DisplayName` annotation to label tests as:
- **"specification-based"**: When testing functionality against provided requirements.
- **"structural-based"**: When testing based on the internal code structure.

---

## Running Tests Locally
To run tests manually on your machine:

1. **Run Static Analysis (Checkstyle)**
   ```sh
   mvn checkstyle:checkstyle
# Assignment_6
# Assignment_6
