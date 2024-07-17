package com.dawidhr.BookLibrary.dao;

import com.dawidhr.BookLibrary.model.BookReservedHistory;

import java.util.List;

public interface BookReservedHistoryDAO {
    List<BookReservedHistory> findAllByPersonId(Long id);
}
