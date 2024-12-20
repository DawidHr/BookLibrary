package com.dawidhr.BookLibrary.cache;

import redis.clients.jedis.UnifiedJedis;

public class RedisCache {
    UnifiedJedis jedis = new UnifiedJedis("redis://localhost:6379");
    private static final long TIME_TO_EXPIRE_IN_SECOND = 360;

    public void addToCache(String key, String value) {
        jedis.setex(key, TIME_TO_EXPIRE_IN_SECOND, value);
    }

    public String getDataFromCache(String key) {
        return jedis.get(key);
    }
}
