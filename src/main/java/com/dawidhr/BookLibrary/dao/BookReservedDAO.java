package com.dawidhr.BookLibrary.dao;

import com.dawidhr.BookLibrary.model.BookReserved;

import java.util.List;
import java.util.Set;

public interface BookReservedDAO {
    List<BookReserved> findAllByPersonId(Long id);
    long count();
    long countBookReservedInLast30Days();
    void delete(BookReserved bookReserved);
    BookReserved getById(long id);
    List<BookReserved> getAllBooksBorrowedAtLeast2MonthsAgo();
    List<BookReserved> getAllBooksBorrowedAtLeast2MonthsAgoByPersonId(long personId);
}
