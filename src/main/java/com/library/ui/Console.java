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
import java.util.Set;
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
            printMainMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> showDashboard();
                    case "2" -> showAllUsers();
                    case "3" -> registerUser();
                    case "4" -> showUserBooks();
                    case "5" -> showAllBooks();
                    case "6" -> searchBooks();
                    case "7" -> addBook();
                    case "8" -> removeBook();
                    case "9" -> borrowBook();
                    case "10" -> returnBook();
                    case "11" -> showOverdueBooks();
                    case "0" -> running = false;
                    default -> showInvalidOption();
                }
            } catch (RuntimeException e) {
                System.out.println();
                System.out.println("Error: " + e.getMessage());
            }
            if (running) {
                pause();
            }
        }
        System.out.println();
        System.out.println("goodbye!");
    }

    private void printMainMenu() {
        System.out.println();
        System.out.println(
                "╔════════════════════════════════════════════════════╗"
        );
        System.out.println(
                "║              LIBRARY MANAGEMENT SYSTEM             ║"
        );
        System.out.println(
                "╠════════════════════════════════════════════════════╣"
        );
        System.out.println(
                "║                                                    ║"
        );
        System.out.println(
                "║  DASHBOARD                                         ║"
        );
        System.out.println(
                "║  1. View dashboard                                 ║"
        );
        System.out.println(
                "║                                                    ║"
        );
        System.out.println(
                "║  USERS                                             ║"
        );
        System.out.println(
                "║  2. View all users                                 ║"
        );
        System.out.println(
                "║  3. Register user                                  ║"
        );
        System.out.println(
                "║  4. View user's books                              ║"
        );
        System.out.println(
                "║                                                    ║"
        );
        System.out.println(
                "║  BOOKS                                             ║"
        );
        System.out.println(
                "║  5. View all books                                 ║"
        );
        System.out.println(
                "║  6. Search books                                   ║"
        );
        System.out.println(
                "║  7. Add book                                       ║"
        );
        System.out.println(
                "║  8. Remove book                                    ║"
        );
        System.out.println(
                "║                                                    ║"
        );
        System.out.println(
                "║  BORROWING                                         ║"
        );
        System.out.println(
                "║  9. Borrow a book                                  ║"
        );
        System.out.println(
                "║  10. Return a book                                 ║"
        );
        System.out.println(
                "║  11. View overdue books                            ║"
        );
        System.out.println(
                "║                                                    ║"
        );
        System.out.println(
                "║  0. Exit                                           ║"
        );
        System.out.println(
                "║                                                    ║"
        );
        System.out.println(
                "╚════════════════════════════════════════════════════╝"
        );
        System.out.print("Choose an option: ");
    }


    private void showDashboard() {
        printHeader("DASHBOARD");
        int totalBooks = libraryService.getTotalBooks();
        int availableBooks = libraryService.getAvailableBooks();
        int borrowedBooks = libraryService.getBorrowedBooks();
        int totalUsers = libraryService.getTotalUsers();
        int activeBorrowings = borrowingService.getActiveBorrowingsCount();
        int overdueBooks = borrowingService.getOverdueBooksCount();
        System.out.println();
        System.out.println("LIBRARY OVERVIEW");
        System.out.println("────────────────────────────────────────");
        System.out.printf(
                "Total books:        %d%n",
                totalBooks
        );
        System.out.printf(
                "Available books:    %d%n",
                availableBooks
        );
        System.out.printf(
                "Borrowed books:     %d%n",
                borrowedBooks
        );
        System.out.println();
        System.out.println("USERS");
        System.out.println("────────────────────────────────────────");

        System.out.printf(
                "Registered users:   %d%n",
                totalUsers
        );
        System.out.println();
        System.out.println("BORROWING");
        System.out.println("────────────────────────────────────────");
        System.out.printf(
                "Active borrowings:  %d%n",
                activeBorrowings
        );
        System.out.printf(
                "Overdue books:      %d%n",
                overdueBooks
        );
        System.out.println();
        System.out.println("────────────────────────────────────────");
    }


    private void showAllBooks() {
        printHeader("ALL BOOKS");
        List<Book> books = libraryService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
            return;
        }
        for (int i = 0; i < books.size(); i++) {
            printBook(i + 1, books.get(i));
        }
        System.out.println();
        System.out.println("Total books: " + books.size());
    }

    private void addBook() {
        printHeader("ADD BOOK");
        System.out.print("Title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Author: ");
        String author = scanner.nextLine().trim();
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine().trim();
        System.out.print("Genre: ");
        String genre = scanner.nextLine().trim();
        if (title.isEmpty()
                || author.isEmpty()
                || isbn.isEmpty()
                || genre.isEmpty()) {
            System.out.println();
            System.out.println("All fields are required.");
            return;
        }
        Book book = new Book(
                title,
                author,
                isbn,
                genre
        );
        libraryService.addBook(book);
        System.out.println();
        System.out.println("Book added successfully.");
    }

    private void removeBook() {
        printHeader("REMOVE BOOK");
        Book book = selectBook(
                libraryService.getAllBooks(),
                "Select a book to remove:"
        );
        if (book == null) {
            return;
        }
        libraryService.removeBook(book.getIsbn());
        System.out.println();
        System.out.println(
                "Book \"" + book.getTitle() + "\" removed successfully."
        );
    }

    private void searchBooks() {
        printHeader("SEARCH BOOKS");
        System.out.print("Search by title, author or genre: ");
        String query = scanner.nextLine().trim();
        if (query.isEmpty()) {
            System.out.println("Search query cannot be empty.");
            return;
        }
        List<Book> books = libraryService.searchBooks(query);
        System.out.println();
        if (books.isEmpty()) {
            System.out.println("No books found.");
            return;
        }
        System.out.println("Search results:");
        System.out.println();
        for (int i = 0; i < books.size(); i++) {
            printBook(i + 1, books.get(i));
        }
        System.out.println();
        System.out.println("Found: " + books.size());
    }


    private void showAllUsers() {
        printHeader("ALL USERS");
        List<User> users = libraryService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No registered users.");
            return;
        }
        for (int i = 0; i < users.size(); i++) {
            printUser(i + 1, users.get(i));
        }
        System.out.println();
        System.out.println("Total users: " + users.size());
    }

    private void registerUser() {
        printHeader("REGISTER USER");
        System.out.print("User ID: ");
        String userId = scanner.nextLine().trim();
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        if (name.isEmpty() || userId.isEmpty() || email.isEmpty()) {
            System.out.println();
            System.out.println("All fields are required.");
            return;
        }
        System.out.println();
        System.out.println("Choose user type:");
        System.out.println("1. Student");
        System.out.println("2. Faculty");
        System.out.println("3. Guest");
        System.out.println();
        System.out.print("Type: ");
        String type = scanner.nextLine().trim();
        User user;
        switch (type) {
            case "1" -> user = new Student(userId, name, email);
            case "2" -> user = new Faculty(userId, name, email);
            case "3" -> user = new Guest(userId, name, email);
            default -> {
                System.out.println("Invalid user type.");
                return;
            }
        }
        libraryService.registerUser(user);
        System.out.println();
        System.out.println("User registered successfully.");
    }


    private void showUserBooks() {
        printHeader("USER'S BOOKS");
        User user = selectUser("Select a user:");
        if (user == null) {
            return;
        }
        Set<String> borrowedIsbns = user.getBorrowedBookIsbns();
        System.out.println();
        System.out.println(
                "Books borrowed by " + user.getName() + ":"
        );
        if (borrowedIsbns.isEmpty()) {
            System.out.println();
            System.out.println("This user has no borrowed books.");
            return;
        }
        List<Book> allBooks = libraryService.getAllBooks();
        int number = 1;
        for (Book book : allBooks) {
            if (borrowedIsbns.contains(book.getIsbn())) {
                printBook(number, book);
                number++;
            }
        }
        System.out.println();
        System.out.println(
                "Currently borrowed: "
                        + borrowedIsbns.size()
        );
    }

    private void borrowBook() {
        printHeader("BORROW BOOK");
        User user = selectUser("Select a user:");
        if (user == null) {
            return;
        }
        System.out.println();
        Book book = selectAvailableBook();
        if (book == null) {
            return;
        }
        borrowingService.borrowBook(user.getUserId(), book.getIsbn());
        System.out.println();
        System.out.println(
                "Book \"" + book.getTitle()
                        + "\" borrowed successfully."
        );
        System.out.println(
                "Due in " + user.getBorrowDays() + " days."
        );
    }

    private void returnBook() {
        printHeader("RETURN BOOK");
        User user = selectUser("Select a user:");
        if (user == null) {
            return;
        }
        List<Book> borrowedBooks = getBooksBorrowedByUser(user);
        if (borrowedBooks.isEmpty()) {
            System.out.println();
            System.out.println(
                    "This user has no borrowed books."
            );
            return;
        }
        System.out.println();
        Book book = selectBook(borrowedBooks, "Select a book to return:");
        if (book == null) {
            return;
        }
        borrowingService.returnBook(
                user.getUserId(),
                book.getIsbn()
        );
        System.out.println();
        System.out.println(
                "Book \"" + book.getTitle()
                        + "\" returned successfully."
        );
    }

    private void showOverdueBooks() {
        printHeader("OVERDUE BOOKS");
        List<BorrowingRecord> overdueBooks = borrowingService.getOverdueBooks();
        if (overdueBooks.isEmpty()) {
            System.out.println("No overdue books.");
            return;
        }
        System.out.println("The following books are overdue:");
        System.out.println();
        for (int i = 0; i < overdueBooks.size(); i++) {
            BorrowingRecord record =
                    overdueBooks.get(i);
            printOverdueRecord(i + 1, record);
        }
        System.out.println();
        System.out.println(
                "Total overdue: " + overdueBooks.size()
        );
    }

    private User selectUser(String message) {
        List<User> users = libraryService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println();
            System.out.println("No registered users.");
            return null;
        }
        System.out.println(message);
        System.out.println();
        for (int i = 0; i < users.size(); i++) {
            printUserShort(i + 1, users.get(i));
        }
        System.out.println();
        System.out.println("0. Back");
        System.out.print("Select a user: ");
        int choice = readNumber();
        if (choice == 0) {
            return null;
        }
        if (choice < 1 || choice > users.size()) {
            System.out.println("Invalid user number.");
            return null;
        }
        return users.get(choice - 1);
    }

    private Book selectAvailableBook() {
        List<Book> allBooks = libraryService.getAllBooks();
        List<Book> availableBooks = allBooks.stream().filter(Book::isAvailable).toList();
        if (availableBooks.isEmpty()) {
            System.out.println("There are no available books.");
            return null;
        }
        return selectBook(availableBooks, "Select an available book:");
    }

    private Book selectBook(List<Book> books, String message) {
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return null;
        }
        System.out.println(message);
        System.out.println();
        for (int i = 0; i < books.size(); i++) {
            printBook(i + 1, books.get(i));
        }
        System.out.println();
        System.out.println("0. Back");
        System.out.print("Select a book: ");
        int choice = readNumber();
        if (choice == 0) {
            return null;
        }
        if (choice < 1 || choice > books.size()) {
            System.out.println("Invalid book number.");
            return null;
        }
        return books.get(choice - 1);
    }

    private List<Book> getBooksBorrowedByUser(User user) {
        Set<String> borrowedIsbns = user.getBorrowedBookIsbns();
        return libraryService.getAllBooks()
                .stream()
                .filter(book -> borrowedIsbns.contains(book.getIsbn())).toList();
    }

    private void printHeader(String title) {
        System.out.println();
        System.out.println(
                "╔════════════════════════════════════════════════════╗"
        );
        System.out.printf(
                "║ %-50s ║%n",
                title
        );
        System.out.println(
                "╚════════════════════════════════════════════════════╝"
        );
        System.out.println();
    }

    private void printBook(int number, Book book) {
        System.out.println(
                number
                        + ". "
                        + book.getTitle()
                        + " — "
                        + book.getAuthor()
        );
        System.out.println(
                "   ISBN: "
                        + book.getIsbn()
                        + " | Genre: "
                        + book.getGenre()
        );
        System.out.println(
                "   Status: "
                        + (book.isAvailable()
                        ? "AVAILABLE"
                        : "BORROWED")
        );
        System.out.println(
                "   ─────────────────────────────────────────"
        );
    }

    private void printUser(int number, User user) {
        System.out.println(
                number
                        + ". Name: "
                        + user.getName()
                        + " ["
                        + getUserType(user)
                        + "]"
        );
        System.out.println(
                "   ID: "
                        + user.getUserId()
                        + " | Email: "
                        + user.getEmail()
        );
        System.out.println(
                "   Books: "
                        + user.getBorrowedBookIsbns().size()
                        + " / "
                        + user.getMaxBooks()
        );
        System.out.println(
                "   ─────────────────────────────────────────"
        );
    }

    private void printUserShort(int number, User user) {
        System.out.println(
                number
                        + ". "
                        + user.getName()
                        + " ["
                        + getUserType(user)
                        + "]"
                        + " — ID: "
                        + user.getUserId()
        );
    }

    private void printOverdueRecord(
            int number,
            BorrowingRecord record
    ) {
        System.out.println(
                number
                        + ". User: "
                        + record.getUserId()
        );
        System.out.println(
                "   Book ISBN: "
                        + record.getIsbn()
        );
        System.out.println(
                "   Borrowed: "
                        + record.getBorrowedAt()
        );
        System.out.println(
                "   Due date: "
                        + record.getDueDate()
        );
        System.out.println(
                "   ─────────────────────────────────────────"
        );
    }

    private String getUserType(User user) {
        if (user instanceof Student) {
            return "STUDENT";
        }
        if (user instanceof Faculty) {
            return "FACULTY";
        }
        if (user instanceof Guest) {
            return "GUEST";
        }
        return "USER";
    }


    private int readNumber() {
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a number.");
            return -1;
        }
    }

    private void showInvalidOption() {
        System.out.println();
        System.out.println("Invalid option. Please try again.");
    }

    private void pause() {
        System.out.println();
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
}