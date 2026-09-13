# Library Management System

Console-based library management system written in Java.

## Features
#### Book management
- Add books
- Remove books
- Search books
#### User management
- Register users
- Support for Student, Faculty, and Guest user types
#### Borrowing operations
- Borrow a book
- Return a book
- Prevent borrowing unavailable books
- Prevent users from exceeding their borrowing limit
- Overdue tracking
- Identify overdue books
#### Console interface
- Menu-based interaction

### Additional functionality
- Dashboard with library statistics
- View all registered users
- View books currently borrowed by a specific user
- View all books with their current availability status
- Search books by title, author, or genre

## Borrowing Rules

| User type | Maximum books | Borrowing period |
| --------- | ------------- | ---------------- |
| Student   | 3             | 14 days          |
| Faculty   | 10            | 30 days          |
| Guest     | 1             | 7 days           |

A book can only be borrowed if it is currently available, and a user cannot exceed their borrowing limit.

## Architecture

The project uses a simple layered architecture:

```text
com.library
├── model        # Domain entities
├── repository   # Data storage and access
├── service      # Business logic
├── ui           # Console interface
├── exception    # Custom exceptions
└── Main.java    # Application entry point
```

Data is stored **in memory** using Java collections. 

## Technologies

* Java 17
* Maven
* Java Collections Framework
* `java.time` API

## Requirements

* JDK 17 or newer
* Maven 3.8+

## Running the Application

Clone the repository and enter the project directory:

```bash
git clone <repository-url>
cd library-management-system
```

Compile and run the application:

```bash
mvn clean compile exec:java
```


## Main Menu

After launching, the application provides the following operations:

```text
1. View dashboard
2. View all users
3. Register user
4. View user's books
5. View all books
6. Search books
7. Add book
8. Remove book
9. Borrow a book
10. Return a book
11. View overdue books
0. Exit
```
