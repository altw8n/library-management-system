package com.library.model;

import java.time.LocalDate;

public class BorrowingRecord {
    private final String userId;
    private final String isbn;
    private final LocalDate borrowedAt;
    private LocalDate returnedAt;
    private final LocalDate dueDate;

    public BorrowingRecord(
            String userId,
            String isbn,
            LocalDate borrowedAt,
            LocalDate dueDate
    ) {
        this.userId = userId;
        this.isbn = isbn;
        this.borrowedAt = borrowedAt;
        this.returnedAt = returnedAt;
        this.dueDate = dueDate;
    }

    public String getUserId() {
        return userId;
    }

    public String getIsbn() {
        return isbn;
    }

    public LocalDate getBorrowedAt() {
        return borrowedAt;
    }

    public LocalDate getReturnedAt() {
        return returnedAt;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        return "BorrowingRecord{" +
                "userId='" + userId + '\'' +
                ", isbn='" + isbn + '\'' +
                ", borrowedAt=" + borrowedAt +
                ", returnedAt=" + returnedAt +
                ", dueDate=" + dueDate +
                '}';
    }

    public boolean isReturned() {
        return returnedAt != null;
    }

    public boolean isOverdue(LocalDate date) {
        return !isReturned() && date.isAfter(dueDate);
    }

    public void markReturned(LocalDate date) {
        if (isReturned()) {
            throw new IllegalStateException("Book has already been returned.");
        }
        returnedAt = date;
    }
}
