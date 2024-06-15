package com.dawidhr.BookLibrary.dao;

import com.dawidhr.BookLibrary.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookDAO {
    Optional<Book> findById(Long id);
    List<Book> getAllBooks();
    Page<Book> getAllBooks(Pageable pageable);
    void insertBook(Book book);
    List<Book> findBook(String title);
    long countAllAvailableBooks();
    long countAddedBooksInLast30Days();
    List<Book> getAllAvailableBooks(Pageable pageable);
}
