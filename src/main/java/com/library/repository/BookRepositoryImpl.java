package com.library.repository;

import com.library.model.Book;

import javax.sql.rowset.serial.SerialStruct;
import java.util.*;

public class BookRepositoryImpl implements BookRepository {
    private final Map<String, Book> bookMap = new HashMap<>();

    @Override
    public void save(Book book){
        bookMap.put(book.getIsbn(), book);
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return Optional.ofNullable(bookMap.get(isbn));
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(bookMap.values());
    }

    @Override
    public boolean deleteByIsbn(String isbn) {
        return bookMap.remove(isbn) != null;
    }
}
