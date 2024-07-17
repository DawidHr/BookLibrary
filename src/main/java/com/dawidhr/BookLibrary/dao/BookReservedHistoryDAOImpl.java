package com.dawidhr.BookLibrary.dao;

import com.dawidhr.BookLibrary.model.BookReserved;
import com.dawidhr.BookLibrary.model.BookReservedHistory;
import com.dawidhr.BookLibrary.repository.BookReservedHistoryRepository;
import com.dawidhr.BookLibrary.repository.BookReservedRepository;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BookReservedHistoryDAOImpl implements BookReservedHistoryDAO {

    @Autowired
    BookReservedHistoryRepository bookReservedHistoryRepository;
    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Override
    public List<BookReservedHistory> findAllByPersonId(Long id) {
        return bookReservedHistoryRepository.findAll().stream().filter(bookReserved -> {return bookReserved.getPerson().getPersonId().equals(id);}).toList();
    }
}
