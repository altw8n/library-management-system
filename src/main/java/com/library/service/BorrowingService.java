package com.library.service;

import com.library.exception.BookNotFoundException;
import com.library.exception.BookUnavailableException;
import com.library.exception.BorrowingLimitExceededException;
import com.library.exception.UserNotFoundException;
import com.library.model.Book;
import com.library.model.BorrowingRecord;
import com.library.model.user.User;
import com.library.repository.BookRepository;
import com.library.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BorrowingService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final List<BorrowingRecord> history;

    public BorrowingService(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.history = new ArrayList<>();
    }

    public void borrowBook(String userId, String isbn) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookNotFoundException(isbn));

        if (!user.canBorrow()){
            throw new BorrowingLimitExceededException(userId);
        }
        if (!book.isAvailable()){
            throw new BookUnavailableException(isbn);
        }
        LocalDate borrowedAt = LocalDate.now();
        LocalDate dueDate = borrowedAt.plusDays(user.getBorrowDays());
        book.borrow();
        user.borrowBook(isbn);
        BorrowingRecord record = new BorrowingRecord(
                userId,
                isbn,
                borrowedAt,
                dueDate
        );
        history.add(record);
    }

    public void returnBook(String userId, String isbn) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookNotFoundException(isbn));
        if (!user.getBorrowedBookIsbns().contains(isbn)) {
            throw new IllegalStateException("this user has not borrowed this book.");
        }
        book.returnBook();
        user.returnBook(isbn);
        BorrowingRecord activeRecord = history.stream()
                .filter(record ->
                        record.getUserId().equals(userId)
                                && record.getIsbn().equals(isbn)
                                && !record.isReturned()
                )
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Active borrowing record not found."
                        )
                );
        activeRecord.markReturned(LocalDate.now());
    }

    public List<BorrowingRecord> getOverdueBooks() {
        LocalDate nowDate = LocalDate.now();
        return history
                .stream()
                .filter(borrowingRecord -> borrowingRecord.isOverdue(nowDate))
                .toList();
    }

    public List<BorrowingRecord> getBorrowingHistory() {
        return List.copyOf(history);
    }
}
