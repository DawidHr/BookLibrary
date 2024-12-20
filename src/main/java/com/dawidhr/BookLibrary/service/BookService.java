package com.dawidhr.BookLibrary.service;

import com.dawidhr.BookLibrary.cache.BookRedisCache;
import com.dawidhr.BookLibrary.dao.BookDAO;
import com.dawidhr.BookLibrary.model.Book;
import com.dawidhr.BookLibrary.model.BookStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookService {
    @Autowired
    BookRedisCache redis;
    @Autowired
    private BookDAO bookDAO;

    public Optional<Book> findBookById(Long id) {
        Book bookFromCache = redis.getBookById(id);
        if(bookFromCache != null) {
            return Optional.of(bookFromCache);
        }
        return bookDAO.findById(id);
    }

    public void deleteBookById(Long id) {
        Optional<Book> optionalBook = findBookById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (BookStatus.AVAILABLE.equals(book.getBookStatus())) {
                book.setDeleted(true);
                bookDAO.insertBook(book);
                redis.removeBookById(book.getBookId());
            }
        }
    }
}
