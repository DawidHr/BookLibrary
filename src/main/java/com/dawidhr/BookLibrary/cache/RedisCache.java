package com.dawidhr.BookLibrary.cache;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.UnifiedJedis;

public class RedisCache {
    @Autowired
    protected Gson gson;
    protected UnifiedJedis jedis = new UnifiedJedis("redis://localhost:6379");
    protected static final long TIME_TO_EXPIRE_IN_SECOND = 360;

    public void addToCache(String key, String value) {
        jedis.setex(key, TIME_TO_EXPIRE_IN_SECOND, value);
    }

    public String getDataFromCache(String key) {
        return jedis.get(key);
    }
}
