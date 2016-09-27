package com.csair.loong;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by cloudoo on 2016/7/22.
 */
public class RedisCluterClient extends RedisClient{

    public RedisCluterClient(){
        this.init();
    }
    @Override
    void init() {
        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
        // Jedis Cluster will attempt to discover cluster nodes automatically

        jedisClusterNodes.add(new HostAndPort("10.92.1.222", 6379));
        jedisClusterNodes.add(new HostAndPort("10.92.1.130", 6379));
        //		jedisClusterNodes.add(new HostAndPort("10.92.1.129", 6379));

        this.jc = new JedisCluster(jedisClusterNodes);
    }
}
