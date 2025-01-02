package com.dawidhr.BookLibrary.cache;


import com.dawidhr.BookLibrary.model.dashboard.StatisticType;
import org.springframework.stereotype.Component;

@Component
public class StatisticCache extends RedisCache {
    private static final String KEY = "redis_statistic";
    private static final String VERSION = "1.0.0";


    public StatisticCache() {
        super();
    }

    public void addObject(StatisticType type, Object object) {
        jedis.setex(prepareKey(type), TIME_TO_EXPIRE_IN_SECOND, gson.toJson(object));
    }

    public Object getObject(StatisticType type) {
        return gson.fromJson(jedis.get(prepareKey(type)), type.getObject().getClass());
    }

    public void removeObject(StatisticType type) {
        jedis.unlink(prepareKey(type));
    }

    private String prepareKey(StatisticType type) {
        return KEY+"_"+type.getTitle()+"_"+VERSION;
    }
}
