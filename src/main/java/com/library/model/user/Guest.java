package com.library.model.user;

public class Guest extends User {

    public Guest(String userId, String name, String email) {
        super(userId, name, email);
    }

    @Override
    public int getMaxBooks() {
        return 1;
    }

    @Override
    public int getBorrowDays() {
        return 7;
    }

    @Override
    public double getFinePerDay() {
        return 1.50;
    }
}