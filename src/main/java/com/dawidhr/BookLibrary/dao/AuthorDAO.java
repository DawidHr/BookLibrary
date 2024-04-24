package com.dawidhr.BookLibrary.dao;

import com.dawidhr.BookLibrary.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDAO {
    Optional<Author> getAuthorById(Long id);
    List<Author> getAllAuthors();
    void insertAuthor(Author author);
}
