package com.dawidhr.BookLibrary.dao;

import com.dawidhr.BookLibrary.model.Author;
import com.dawidhr.BookLibrary.repository.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AuthorDAOImpl implements AuthorDAO {

    private final AuthorRepository authorRepository;

    public AuthorDAOImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public void insertAuthor(Author author) {
        authorRepository.save(author);
    }

}
