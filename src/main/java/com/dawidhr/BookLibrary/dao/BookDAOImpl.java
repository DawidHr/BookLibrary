package com.dawidhr.BookLibrary.dao;

import com.dawidhr.BookLibrary.model.Book;
import com.dawidhr.BookLibrary.repository.BookRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Component
public class BookDAOImpl implements BookDAO {

    private final BookRepository bookRepository;
    private final EntityManagerFactory entityManagerFactory;

    public BookDAOImpl(BookRepository bookRepository, EntityManagerFactory entityManagerFactory) {
        this.bookRepository = bookRepository;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void insertBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public List<Book> findBook(String title) {
        TypedQuery<Book> query = entityManagerFactory.createEntityManager().createQuery("""
                SELECT b FROM Book b WHERE b.bookInfo.title like :title 
                """, Book.class);
        query.setParameter("title", "%"+title+"%");
        return query.getResultList();
    }

    @Override
    public long countAllAvailableBooks() {
        TypedQuery<Long> query = entityManagerFactory.createEntityManager().createQuery("""
                SELECT count(b) FROM Book b WHERE b.isDeleted = false
                """, Long.class);
        return query.getSingleResult();
    }

    @Override
    public long countAddedBooksInLast30Days() {
        TypedQuery<Long> query = entityManagerFactory.createEntityManager().createQuery("""
                SELECT count(b) FROM Book b WHERE b.isDeleted = false AND b.creationDate > :last30days 
                """, Long.class);
        query.setParameter("last30days",new Timestamp(System.currentTimeMillis()).toLocalDateTime().minusMonths(1));
        return query.getSingleResult();
    }

    @Override
    public List<Book> getAllAvailableBooks(Pageable pageable) {
        int offset = (int) pageable.getOffset();
        TypedQuery<Book> query = entityManagerFactory.createEntityManager().createQuery("""
                SELECT b FROM Book b WHERE b.isDeleted = False
                """, Book.class);
        query.setMaxResults(pageable.getPageSize());
        query.setFirstResult(offset);
        return query.getResultList();
    }

    @Override
    public List<Book> getAllAvailableNotDeletedBooks(Pageable pageable) {
        int offset = (int) pageable.getOffset();
        System.out.println("Offset = "+offset);
        TypedQuery<Book> query = entityManagerFactory.createEntityManager().createQuery("""
                SELECT b FROM Book b WHERE b.isDeleted = False and b.bookStatus = 'AVAILABLE'
                """, Book.class);
        query.setMaxResults(10);
        query.setFirstResult(offset);
        return query.getResultList();
    }
}
