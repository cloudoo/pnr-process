package com.csair.loong;


import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by cloudoo on 2016/7/22.
 */
public class RedisSingleClient extends  RedisClient{

    public RedisSingleClient(){
        this.init();
    }
    @Override
    void init() {
        JedisPoolConfig conf = new JedisPoolConfig();

        conf.setMaxIdle(2000);
        conf.setMinIdle(1000);
        conf.setTestWhileIdle(false);
        conf.setTestOnBorrow(true);
        conf.setTestOnReturn(false);
        conf.setNumTestsPerEvictionRun(10);
        conf.setMinEvictableIdleTimeMillis(864000000);
        conf.setSoftMinEvictableIdleTimeMillis(10);
        conf.setTimeBetweenEvictionRunsMillis(300000);
        conf.setLifo(false);

        jedisPool = new JedisPool(conf,"10.92.1.129",6379,1000000,null,0);

    }

    public Long push(String key, String... value) {

        jedis =  jedisPool.getResource();
        Long size = jedis.rpush(key,value);
        jedis.close();
        return size;

    }

    public String pull(String key) {
        jedis =  jedisPool.getResource();
        String value = jedis.lpop(key);
        jedis.close();
        return value;
    }


}
