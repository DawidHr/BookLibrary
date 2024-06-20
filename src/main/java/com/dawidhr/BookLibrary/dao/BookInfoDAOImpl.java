package com.dawidhr.BookLibrary.dao;

import com.dawidhr.BookLibrary.model.BookInfo;
import com.dawidhr.BookLibrary.repository.BookInfoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class BookInfoDAOImpl implements BookInfoDAO {

    BookInfoRepository bookInfoRepository;

    public BookInfoDAOImpl(BookInfoRepository bookInfoRepository) {
        this.bookInfoRepository = bookInfoRepository;
    }

    @Transactional
    @Override
    public void insertBookInfo(BookInfo bookInfo) {
        bookInfoRepository.save(bookInfo);
    }
}
