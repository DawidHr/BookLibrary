package com.dawidhr.BookLibrary.dao;

import com.dawidhr.BookLibrary.model.BookReserved;
import com.dawidhr.BookLibrary.repository.BookReservedRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class BookReservedDAOImpl implements BookReservedDAO {

    @Autowired
    BookReservedRepository bookReservedRepository;
    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Override
    public List<BookReserved> findAllByPersonId(Long id) {
        return bookReservedRepository.findAll().stream().filter(bookReserved -> {return bookReserved.getPerson().getPersonId().equals(id);}).toList();
    }

    @Override
    public long count() {
        return bookReservedRepository.count();
    }

    @Override
    public long countBookReservedInLast30Days() {
        TypedQuery<Long> query = entityManagerFactory.createEntityManager().createQuery("""
                SELECT count(b) FROM BookReserved b WHERE b.creationDate > :last30days 
                """, Long.class);
        query.setParameter("last30days", new Timestamp(System.currentTimeMillis()).toLocalDateTime().minusMonths(1));
        return query.getSingleResult();
    }

}
