package com.dawidhr.BookLibrary.dao;

import com.dawidhr.BookLibrary.model.Author;
import com.dawidhr.BookLibrary.model.Book;
import com.dawidhr.BookLibrary.model.BookInfo;
import com.dawidhr.BookLibrary.repository.BookInfoRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookInfoDAOImpl implements BookInfoDAO {

    @Autowired
    BookInfoRepository bookInfoRepository;
    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Transactional
    @Override
    public void insertBookInfo(BookInfo bookInfo) {
        bookInfoRepository.save(bookInfo);
    }

    @Override
    public BookInfo findByTitle(String title) {
        TypedQuery<BookInfo> query = entityManagerFactory.createEntityManager().createQuery("""
                SELECT b FROM BookInfo b WHERE b.title like :find 
                """, BookInfo.class);
        query.setParameter("find", "%"+title+"%");
        List<BookInfo> books = query.getResultList();
        return books.isEmpty() ? null : books.get(0);
    }

    @Override
    public List<BookInfo> getAllBooksInfo() {
        return bookInfoRepository.findAll();
    }
}
