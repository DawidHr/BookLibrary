package com.dawidhr.BookLibrary.repository;

import com.dawidhr.BookLibrary.model.BookReserved;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReservedRepository extends JpaRepository<BookReserved, Long> {
}
