package com.dawidhr.BookLibrary.repository;

import com.dawidhr.BookLibrary.model.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
