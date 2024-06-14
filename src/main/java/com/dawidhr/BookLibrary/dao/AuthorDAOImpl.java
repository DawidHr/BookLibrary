package com.dawidhr.BookLibrary.dao;

import com.dawidhr.BookLibrary.model.Author;
import com.dawidhr.BookLibrary.repository.AuthorRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Component
public class AuthorDAOImpl implements AuthorDAO {

    private final AuthorRepository authorRepository;
    private final EntityManagerFactory entityManagerFactory;

    public AuthorDAOImpl(AuthorRepository authorRepository, EntityManagerFactory entityManagerFactory) {
        this.authorRepository = authorRepository;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public void insertAuthor(Author author) {
        authorRepository.save(author);
    }

    @Override
    public Page<Author> getAllAuthors(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    @Override
    public long count() {
        return authorRepository.count();
    }

    @Override
    public List<Author> findAuthor(String find) {
        TypedQuery<Author> query = entityManagerFactory.createEntityManager().createQuery("""
                SELECT b FROM Author b WHERE b.lastName like :find 
                """, Author.class);
        query.setParameter("find", "%"+find+"%");
        return query.getResultList();
    }

    @Override
    public long countAddedAuthorInLast30Days() {
        TypedQuery<Long> query = entityManagerFactory.createEntityManager().createQuery("""
                SELECT COUNT(b) FROM Author b WHERE b.creationDate > :last30days 
                """, Long.class);
        query.setParameter("last30days",new Timestamp(System.currentTimeMillis()).toLocalDateTime().minusMonths(1));
        return query.getSingleResult();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

}
