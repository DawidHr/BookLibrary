package com.dawidhr.BookLibrary.dao;

import com.dawidhr.BookLibrary.model.BookInfo;

import java.util.List;


public interface BookInfoDAO {
    void insertBookInfo(BookInfo bookInfo);
    BookInfo findByTitle(String title);
    List<BookInfo> getAllBooksInfo();
}
