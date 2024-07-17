package com.dawidhr.BookLibrary.dao;

import com.dawidhr.BookLibrary.model.BookReserved;
import com.dawidhr.BookLibrary.repository.BookReservedRepository;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BookReservedDAOImpl implements BookReservedDAO {

    @Autowired
    BookReservedRepository bookReservedRepository;
    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Override
    public List<BookReserved> findAllByPersonId(Long id) {
        return bookReservedRepository.findAll().stream().filter(bookReserved -> {return bookReserved.getPerson().getPersonId().equals(id);}).toList();
    }
}
