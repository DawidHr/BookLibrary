package com.dawidhr.BookLibrary.cache;

import com.dawidhr.BookLibrary.model.Book;
import com.dawidhr.BookLibrary.model.dashboard.AuthorDashboard;
import com.dawidhr.BookLibrary.model.dashboard.BookDashboard;
import com.dawidhr.BookLibrary.model.dashboard.BookReservedDashboard;
import com.dawidhr.BookLibrary.model.dashboard.PersonDashboard;

public class StatisticCache extends RedisCache {
    private static final String KEY = "redis_statistic";
    private static final String VERSION = "1.0.0";


    public StatisticCache() {
        super();
    }

    public void addAuthorDashboard(AuthorDashboard authorDashboard) {
        jedis.setex(prepareKey(authorDashboard), TIME_TO_EXPIRE_IN_SECOND, gson.toJson(authorDashboard));
    }

    public AuthorDashboard getAuthorDashboard(AuthorDashboard authorDashboard) {
        return gson.fromJson(jedis.get(prepareKey(authorDashboard)), AuthorDashboard.class);
    }

    public void removeAuthorDashboard(AuthorDashboard authorDashboard) {
        jedis.unlink(prepareKey(authorDashboard));
    }

    public void addBookDashboard(BookDashboard bookDashboard) {
        jedis.setex(prepareKey(bookDashboard), TIME_TO_EXPIRE_IN_SECOND, gson.toJson(bookDashboard));
    }

    public BookDashboard getAuthorDashboard(BookDashboard bookDashboard) {
        return gson.fromJson(jedis.get(prepareKey(bookDashboard)), BookDashboard.class);
    }

    public void removeAuthorDashboard(BookDashboard bookDashboard) {
        jedis.unlink(prepareKey(bookDashboard));
    }

    public void addBookReservedDashboard(BookReservedDashboard bookReservedDashboard) {
        jedis.setex(prepareKey(bookReservedDashboard), TIME_TO_EXPIRE_IN_SECOND, gson.toJson(bookReservedDashboard));
    }

    public BookReservedDashboard getBookReservedDashboard(BookReservedDashboard bookReservedDashboard) {
        return gson.fromJson(jedis.get(prepareKey(bookReservedDashboard)), BookReservedDashboard.class);
    }

    public void removeBookReservedDashboard(BookReservedDashboard bookReservedDashboard) {
        jedis.unlink(prepareKey(bookReservedDashboard));
    }

    public void addPersonDashboard(PersonDashboard personDashboard) {
        jedis.setex(prepareKey(personDashboard), TIME_TO_EXPIRE_IN_SECOND, gson.toJson(personDashboard));
    }

    public PersonDashboard getPersonDashboard(PersonDashboard personDashboard) {
        return gson.fromJson(jedis.get(prepareKey(personDashboard)), PersonDashboard.class);
    }

    public void removePersonDashboard(PersonDashboard personDashboard) {
        jedis.unlink(prepareKey(personDashboard));
    }

    private String prepareKey(Object object) {
        return KEY+"_"+object.getClass().getName()+"_"+VERSION;
    }
}
