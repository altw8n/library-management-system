package com.library.model.user;

public class Student extends User {

    public Student(String name, String userId, String email) {
        super(name, userId, email);
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