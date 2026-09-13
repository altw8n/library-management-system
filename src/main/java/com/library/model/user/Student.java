package com.library.model.user;

public class Student extends User {

    public Student(String userId, String name, String email) {
        super(userId, name, email);
    }

    @Override
    public int getMaxBooks() {
        return 3;
    }

    @Override
    public int getBorrowDays() {
        return 14;
    }

    @Override
    public double getFinePerDay() {
        return 0.50;
    }
}