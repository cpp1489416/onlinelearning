package cpp.common.redis;

import com.google.gson.Gson;
import cpp.common.tools.SerializeUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.*;

@Component
@Scope("prototype") // 权限缓存和认证信息缓存，多例
public class ShiroRedisCache<K, V> implements Cache<K, V> {
    @Autowired
    private RedisTemplate redisTemplate;
    private String prefix = "shiro_redis_cache:";
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix + name + ":";
    }

    @Override
    public V get(K k) throws CacheException {
        if (k == null) {
            return null;
        }

        String key = getPrefix() + k.toString();
        Object ans = (redisTemplate.opsForValue().get(key));
        return (V) ans;
    }

    @Override
    public V put(K k, V v) throws CacheException {
        if (k == null || v == null) {
            return null;
        }
        String key = getPrefix() + k.toString();

        redisTemplate.opsForValue().set(key, v);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        if (k == null) {
            return null;
        }
        String key = getPrefix() + k.toString();
        V v = (V) redisTemplate.opsForValue().get(key);
        redisTemplate.delete(key);
        return v;
    }

    @Override
    public void clear() throws CacheException {
        redisTemplate.delete(keys());
    }

    @Override
    public int size() {
        return keys().size();
    }

    @Override
    public Set<K> keys() {
        Set<K> keys = redisTemplate.<K>keys(getPrefix() + "*");

        return keys;
    }

    @Override
    public Collection<V> values() {
        Set<K> keys = keys();
        List<V> values = new ArrayList<>(keys.size());
        for (K k : keys) {
            values.add(get(k));
        }
        return values;
    }
}