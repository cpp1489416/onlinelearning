package cpp.common.redis;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component("redisSessionDao")
public class RedisSessionDao extends AbstractSessionDAO {
    @Autowired
    private RedisTemplate redisTemplate;
    @Value("24") // 一个session24小时存活
    private int SessionHours;
    private String keyPrefix = "shiro_redis_session:";

    private String getKey(String originalKey) {
        return keyPrefix + originalKey;
    }

    @PostConstruct
    public void init() {
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        try {
            redisTemplate.opsForValue().set(getKey(session.getId().toString()), session, SessionHours, TimeUnit.HOURS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Session session) {
        try {
            String key = getKey(session.getId().toString());
            redisTemplate.delete(key);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Collection<Session> getActiveSessions() {
        // 暂时没有实现此功能
        Set<Serializable> keys = redisTemplate.keys(getKey("*"));
        List<Session> sessions = new ArrayList<>();
        for (Serializable key : keys) {
            sessions.add((Session) redisTemplate.opsForValue().get(key));
        }
        return sessions;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        try {
            redisTemplate.opsForValue().set(getKey(session.getId().toString()), session, SessionHours, TimeUnit.HOURS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session readSession = null;
        try {
            readSession = (Session) redisTemplate.opsForValue().get(getKey(sessionId.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readSession;
    }

}
