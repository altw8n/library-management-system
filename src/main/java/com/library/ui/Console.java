package com.library.ui;

import com.library.model.Book;
import com.library.model.BorrowingRecord;
import com.library.model.user.Faculty;
import com.library.model.user.Guest;
import com.library.model.user.Student;
import com.library.model.user.User;
import com.library.service.BorrowingService;
import com.library.service.LibraryService;

import java.util.List;
import java.util.Scanner;

public class Console {

    private final LibraryService libraryService;
    private final BorrowingService borrowingService;

    private final Scanner scanner;

    public Console(
            LibraryService libraryService,
            BorrowingService borrowingService
    ) {
        this.libraryService = libraryService;
        this.borrowingService = borrowingService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {

        boolean running = true;

        while (running) {
            printMenu();

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1" -> addBook();
                    case "2" -> removeBook();
                    case "3" -> searchBooks();
                    case "4" -> registerUser();
                    case "5" -> borrowBook();
                    case "6" -> returnBook();
                    case "7" -> showOverdueBooks();
                    case "0" -> running = false;
                    default -> System.out.println("Invalid option.");
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println();
        }

        System.out.println("Goodbye!");
    }

    private void printMenu() {
        System.out.println("===== Library Management System =====");
        System.out.println("1. Add book");
        System.out.println("2. Remove book");
        System.out.println("3. Search books");
        System.out.println("4. Register user");
        System.out.println("5. Borrow book");
        System.out.println("6. Return book");
        System.out.println("7. Show overdue books");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    private void addBook() {

        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("Author: ");
        String author = scanner.nextLine();

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Genre: ");
        String genre = scanner.nextLine();

        Book book = new Book(title, author, isbn, genre);

        libraryService.addBook(book);

        System.out.println("Book added successfully.");
    }

    private void removeBook() {

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        libraryService.removeBook(isbn);

        System.out.println("Book removed successfully.");
    }

    private void searchBooks() {

        System.out.print("Search: ");
        String query = scanner.nextLine();

        List<Book> books = libraryService.searchBooks(query);

        if (books.isEmpty()) {
            System.out.println("No books found.");
            return;
        }

        for (Book book : books) {
            System.out.println(book);
        }
    }

    private void registerUser() {

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("User ID: ");
        String userId = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.println("Choose user type:");
        System.out.println("1. Student");
        System.out.println("2. Faculty");
        System.out.println("3. Guest");

        String type = scanner.nextLine();

        User user;

        switch (type) {
            case "1" -> user = new Student(name, userId, email);
            case "2" -> user = new Faculty(name, userId, email);
            case "3" -> user = new Guest(name, userId, email);
            default -> {
                System.out.println("Invalid user type.");
                return;
            }
        }

        libraryService.registerUser(user);

        System.out.println("User registered successfully.");
    }

    private void borrowBook() {

        System.out.print("User ID: ");
        String userId = scanner.nextLine();

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        borrowingService.borrowBook(userId, isbn);

        System.out.println("Book borrowed successfully.");
    }

    private void returnBook() {

        System.out.print("User ID: ");
        String userId = scanner.nextLine();

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        borrowingService.returnBook(userId, isbn);

        System.out.println("Book returned successfully.");
    }

    private void showOverdueBooks() {

        List<BorrowingRecord> overdueBooks =
                borrowingService.getOverdueBooks();

        if (overdueBooks.isEmpty()) {
            System.out.println("No overdue books.");
            return;
        }

        System.out.println("Overdue books:");

        for (BorrowingRecord record : overdueBooks) {
            System.out.println(record);
        }
    }
}