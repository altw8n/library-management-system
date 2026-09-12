package com.library.model.user;

import java.util.HashSet;
import java.util.Set;

public abstract class User {
    private final String userId;
    private final String name;
    private final String email;
    private final Set<String> borrowedBookIsbns;

    protected User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.borrowedBookIsbns = new HashSet<>();
    }

    public abstract int getMaxBooks();
    public abstract int getBorrowDays();
    public abstract double getFinePerDay();

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Set<String> getBorrowedBookIsbns() {
        return borrowedBookIsbns;
    }

    public boolean canBorrow() {
        return borrowedBookIsbns.size() < getMaxBooks();
    }

    public void borrowBook(String isbn) {
        if (!canBorrow()){
            throw new IllegalStateException("user has reached the limit.");
        }
        borrowedBookIsbns.add(isbn);
    }

    public void returnBook(String isbn) {
        borrowedBookIsbns.remove(isbn);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
