package com.dawidhr.BookLibrary.dao;

import com.dawidhr.BookLibrary.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AuthorDAO {
    Optional<Author> getAuthorById(Long id);
    List<Author> getAllAuthors();
    void insertAuthor(Author author);
    Page<Author> getAllAuthors(Pageable pageable);
    long count();
    List<Author> findAuthor(String find);
    long countAddedAuthorInLast30Days();
}
