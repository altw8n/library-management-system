package com.library.model.user;

public class Faculty extends User {
    public Faculty(String userId, String name, String email) {
        super(userId, name, email);
    }

    @Override
    public int getMaxBooks() {
        return 10;
    }

    @Override
    public int getBorrowDays() {
        return 30;
    }

    @Override
    public double getFinePerDay() {
        return 1.00;
    }
}
