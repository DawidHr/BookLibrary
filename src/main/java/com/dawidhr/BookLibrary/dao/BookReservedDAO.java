package com.dawidhr.BookLibrary.dao;

import com.dawidhr.BookLibrary.model.BookReserved;

import java.util.List;

public interface BookReservedDAO {
    List<BookReserved> findAllByPersonId(Long id);
}
