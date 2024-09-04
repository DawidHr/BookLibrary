package com.dawidhr.BookLibrary.dao;

import com.dawidhr.BookLibrary.model.BookReserved;
import com.dawidhr.BookLibrary.repository.BookReservedRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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

    @Override
    @Transactional
    public void delete(BookReserved bookReserved) {
        try {
            bookReservedRepository.delete(bookReserved);
            bookReservedRepository.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public BookReserved getById(long id) {
        return bookReservedRepository.getReferenceById(id);
    }

    @Override
    public List<BookReserved> getAllBooksBorrowedAtLeast2MonthsAgo() {
        TypedQuery<BookReserved> query = entityManagerFactory.createEntityManager().createQuery("""
                SELECT * FROM BookReserved b WHERE b.creationDate <= :creationDate
                """, BookReserved.class);
        query.setParameter("creationDate", Timestamp.from(Instant.now().minus(2, ChronoUnit.MONTHS)));
        return query.getResultList();
    }

    @Override
    public List<BookReserved> getAllBooksBorrowedAtLeast2MonthsAgoByPersonId(long personId) {
        TypedQuery<BookReserved> query = entityManagerFactory.createEntityManager().createQuery("""
                SELECT * FROM BookReserved b WHERE b.creationDate <= :creationDate AND b.Person.personId = :personId
                """, BookReserved.class);
        query.setParameter("creationDate", Timestamp.from(Instant.now().minus(2, ChronoUnit.MONTHS)));
        query.setParameter("personId", personId);
        return query.getResultList();
    }


}
