package test;

import cpp.common.redis.RedisUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisDataException;

import java.io.Serializable;
import java.util.Scanner;

public class JedisTest {

    private static class Person implements Serializable {
        private int name;
        public Person() {
            name = 32523;
        }

        public int getName() {
            return name;
        }

        public void setName(int name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name=" + name +
                    '}';
        }
    }

    public static void main(String[] args) {
        test2();

    }

    public static  void test2() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:redis.xml");
        RedisUtil redisUtil = (RedisUtil) applicationContext.getBean("redisUtil");
        redisUtil.set("string1", new Person());

        System.out.println(redisUtil.get("string1"));
    }

    public static void test1() {
        JedisDataException ex ;
        Jedis jedis = new Jedis("112.74.44.117");
        jedis.auth("z5200226");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            System.out.println(jedis.get(scanner.next()));
        }

    }
}
