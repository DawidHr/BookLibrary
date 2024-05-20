package com.dawidhr.BookLibrary.dao;

import com.dawidhr.BookLibrary.model.Book;
import com.dawidhr.BookLibrary.repository.BookRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAOImpl implements BookDAO {

    private final BookRepository bookRepository;
    private final EntityManagerFactory entityManagerFactory;

    public BookDAOImpl(BookRepository bookRepository, EntityManagerFactory entityManagerFactory) {
        this.bookRepository = bookRepository;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
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
    public void insertBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public List<Book> findBook(String title) {
        System.out.println("Tytuł = "+title);
        TypedQuery<Book> query = entityManagerFactory.createEntityManager().createQuery("""
                SELECT b FROM Book b WHERE b.title like :title 
                """, Book.class);
        query.setParameter("title", "%"+title+"%");
        System.out.println("Tytuł = "+title);
        return query.getResultList();
    }
}
