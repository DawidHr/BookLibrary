package com.dawidhr.BookLibrary.repository;

import com.dawidhr.BookLibrary.model.BookReservedHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReservedHistoryRepository extends JpaRepository<BookReservedHistory, Long> {
}
