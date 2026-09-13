package com.library.service;

import com.library.exception.BookNotFoundException;
import com.library.exception.UserNotFoundException;
import com.library.model.Book;
import com.library.model.user.User;
import com.library.repository.BookRepository;
import com.library.repository.UserRepository;

import java.util.List;

public class LibraryService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public LibraryService(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public void removeBook(String isbn) {
        boolean r = bookRepository.deleteByIsbn(isbn);
        if (!r) {
            throw new BookNotFoundException(isbn);
        }
    }

    public Book findBook(String isbn) {
        return bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public List<Book> searchBooks(String query) {
        String normQuery = query.toLowerCase().trim();
        return bookRepository.findAll()
                .stream()
                .filter(book ->
                        book.getTitle().toLowerCase().contains(normQuery)
                                || book.getAuthor().toLowerCase().contains(normQuery)
                                || book.getGenre().toLowerCase().contains(normQuery)
                )
                .toList();
    }


    public void registerUser(User user) {
        userRepository.save(user);
    }

    public User findUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public int getTotalBooks() {
        return bookRepository.findAll().size();
    }

    public int getAvailableBooks() {
        long cnt = bookRepository.findAll().stream().filter(Book::isAvailable).count();
        return (int) cnt;
    }

    public int getBorrowedBooks() {
        long cnt = bookRepository.findAll().stream().filter(book -> !book.isAvailable()).count();
        return (int) cnt;
    }

    public int getTotalUsers() {
        return userRepository.findAll().size();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
