package com.dawidhr.BookLibrary.service;

import com.dawidhr.BookLibrary.cache.StatisticCache;
import com.dawidhr.BookLibrary.dao.AuthorDAO;
import com.dawidhr.BookLibrary.dao.BookDAO;
import com.dawidhr.BookLibrary.dao.BookReservedDAO;
import com.dawidhr.BookLibrary.dao.PersonDAO;
import com.dawidhr.BookLibrary.model.dashboard.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatisticService {
    @Autowired
    StatisticCache cache;
    @Autowired
    AuthorDAO authorDAO;
    @Autowired
    BookDAO bookDAO;
    @Autowired
    PersonDAO personDAO;
    @Autowired
    BookReservedDAO bookReservedDAO;

    public AuthorDashboard getAuthorDashboard() {
        AuthorDashboard authorDashboard = (AuthorDashboard) cache.getObject(StatisticType.AUTHOR_DASHBOARD);
        if(authorDashboard != null) {
            return authorDashboard;
        }
        long authorsCount = authorDAO.count();
        long authorsAddedLast30DaysCount = authorDAO.countAddedAuthorInLast30Days();
        authorDashboard = new AuthorDashboard(authorsCount, authorsAddedLast30DaysCount);
        cache.addObject(StatisticType.AUTHOR_DASHBOARD, authorDashboard);
        return authorDashboard;
    }

    public PersonDashboard getPersonDashboard() {
        PersonDashboard personDashboard = (PersonDashboard) cache.getObject(StatisticType.PERSON_DASHBOARD);
        if(personDashboard != null) {
            return personDashboard;
        }
        long personCount = personDAO.count();
        long personRegisteredInLast30DaysCount = personDAO.countPersonRegisteredInLast30Days();
        personDashboard = new PersonDashboard(personCount, personRegisteredInLast30DaysCount);
        cache.addObject(StatisticType.PERSON_DASHBOARD, personDashboard);
        return personDashboard;
    }

    public BookDashboard getBookDashboard() {
        BookDashboard bookDashboard = (BookDashboard) cache.getObject(StatisticType.BOOK_DASHBOARD);
        if(bookDashboard != null) {
            return bookDashboard;
        }
        long booksCount = bookDAO.countAllAvailableBooks();
        long booksAddedLast30DaysCount = bookDAO.countAddedBooksInLast30Days();
        bookDashboard = new BookDashboard(booksCount, booksAddedLast30DaysCount);
        cache.addObject(StatisticType.BOOK_DASHBOARD, bookDashboard);
        return bookDashboard;
    }

    public BookReservedDashboard getBookReservedDashboard() {
        BookReservedDashboard bookReservedDashboard = (BookReservedDashboard) cache.getObject(StatisticType.BOOK_RESERVED_DASHBOARD);
        if(bookReservedDashboard != null) {
            return bookReservedDashboard;
        }
        long bookReservedCount = bookReservedDAO.count();
        long bookReservedInLast30DaysCount = bookReservedDAO.countBookReservedInLast30Days();
        bookReservedDashboard = new BookReservedDashboard(bookReservedCount, bookReservedInLast30DaysCount);
        cache.addObject(StatisticType.BOOK_RESERVED_DASHBOARD, bookReservedDashboard);
        return bookReservedDashboard;
    }
}
