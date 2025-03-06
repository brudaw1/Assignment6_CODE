# SE 333 Software Testing - Assignment 6

## Overview

###  Barnes & Noble Shopping System
![Build Status](https://github.com/brudaw1/Assignment6_code/actions/workflows/SE333_CI.yml/badge.svg)

This project is a **Java-based** implementation of a **Barnes & Noble** book purchasing system. It includes:
-  **Book Database** – Stores book information.
-  **Purchase Process** – Handles book purchases.
-  **Shopping System** – Integrates different operations.

---

## Part 1: Software Testing

This part focuses on testing the `BarnesAndNoble` project using two approaches:
- **Specification-based Testing**: Tests based on provided system requirements.
- **Structural-based Testing**: Tests based on internal code structure.

###  Testing Tools
- **JUnit 5** – For writing and executing test cases.
- **Mockito** – For mocking dependencies in unit tests.
- **AssertJ** – For enhanced assertions and readability.

### - Commit Requirement
Commit Message:
```
{your name} + Part 1 added BarnesAndNoble Tests
```

---

## Part 2: Automating Testing with GitHub Actions

This part introduces **continuous integration (CI)** through **GitHub Actions**.

###  CI Workflow
Each push to the `main` branch triggers:
1. **Static Analysis (Checkstyle)** – Ensures code quality.
2. **Unit Tests Execution (JUnit & Mockito)** – Runs all test cases.
3. **Code Coverage Analysis (Jacoco)** – Measures test coverage.

###  Automated Reports
GitHub Actions **automatically generates** the following artifacts:
-  **Checkstyle Report (`checkstyle-result.xml`)** – Code style analysis.
-  **Jacoco Code Coverage Report (`jacoco-report`)** – Coverage details.

###  Additional Notes
- **Checkstyle** runs in the **validate phase**, ensuring the code follows style guidelines before testing.
- **Jacoco Coverage Analysis** is included in the `pom.xml`.
- Artifacts are **uploaded using GitHub Actions artifacts feature**.

###  Commit Requirement
Commit Message:
```
{your name} + added Part2 Workflow
```

---

##  Part 3: Writing Larger Tests

This section expands the test suite by adding **unit tests** and **integration tests** for the **Amazon package**.

###  Testing Objectives
- **Integration Testing** – Verifies interactions between components (e.g., `Amazon`, `ShoppingCartAdaptor`, and the database).
    -  Resets the database before each test for consistency.
- **Unit Testing** – Focuses on individual components using test doubles (mocks/stubs).

###  Test Organization
- **`AmazonUnitTest.java`** – Contains unit tests for Amazon functionalities.
    -  **Specification-based tests** – Validates expected behaviors.
    -  **Structural-based tests** – Ensures correct internal interactions.
- **`AmazonIntegrationTest.java`** – Covers system-wide interactions with a real database.

###  Testing Annotations
- `@DisplayName("specification-based")` – Labels functional tests.
- `@DisplayName("structural-based")` – Labels internal logic tests.

###  Commit Requirement
Commit Message:
```
{your name} + added Part3 Amazon Tests
```

---

##  Running Tests Locally

To run tests manually on your machine:

###  Run Static Analysis (Checkstyle)
```sh
mvn checkstyle:checkstyle
```

###  Run Unit and Integration Tests
```sh
mvn test
```

###  Generate Jacoco Coverage Report
```sh
mvn jacoco:report
```
