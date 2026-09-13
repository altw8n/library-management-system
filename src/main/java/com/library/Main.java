package com.library;


import com.library.repository.BookRepository;
import com.library.repository.BookRepositoryImpl;
import com.library.repository.UserRepositoryImpl;
import com.library.repository.UserRepository;
import com.library.service.BorrowingService;
import com.library.service.LibraryService;
import com.library.ui.Console;

public class Main {

    public static void main(String[] args) {
        BookRepository bookRepository = new BookRepositoryImpl();
        UserRepository userRepository = new UserRepositoryImpl();
        LibraryService libraryService = new LibraryService(bookRepository, userRepository);
        BorrowingService borrowingService = new BorrowingService(bookRepository, userRepository);
        Console console = new Console(libraryService, borrowingService);
        console.start();
    }
}