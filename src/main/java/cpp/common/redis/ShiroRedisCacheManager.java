package cpp.common.redis;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ShiroRedisCacheManager implements CacheManager, ApplicationContextAware {
    ApplicationContext applicationContext;
    Map<String, Cache> cacheMap = new HashMap<>();

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        if (cacheMap.containsKey(s)) {
            return cacheMap.get(s);
        } else {
            ShiroRedisCache cache = applicationContext.getBean(ShiroRedisCache.class);
            cache.setName(s);
            cacheMap.put(s, cache);
            return cache;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
