package com.dawidhr.BookLibrary.cache;

import com.dawidhr.BookLibrary.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookRedisCache extends RedisCache {
    private static final String KEY = "redis_book";
    private static final String VERSION = "1.0.0";


    public BookRedisCache() {
        super();
    }

    public void addBookById(Long id, Book book) {
        jedis.setex(prepareKey(id), TIME_TO_EXPIRE_IN_SECOND, gson.toJson(book));
    }

    public Book getBookById(Long id) {
        return gson.fromJson(jedis.get(prepareKey(id)), Book.class);
    }

    private String prepareKey(Long id) {
        return KEY+"_"+VERSION+"_"+id;
    }
}
