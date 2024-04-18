package com.dawidhr.BookLibrary.dao;

import com.dawidhr.BookLibrary.model.Author;

import java.util.List;

public interface AuthorDAO {
    Author getAuthorById(Long id);
    List<Author> getAllAuthors();
    void insertAuthor(Author author);
}
