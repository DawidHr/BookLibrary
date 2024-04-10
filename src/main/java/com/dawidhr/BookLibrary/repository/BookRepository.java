package com.dawidhr.BookLibrary.repository;

import com.dawidhr.BookLibrary.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
