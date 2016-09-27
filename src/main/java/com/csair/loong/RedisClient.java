package com.csair.loong;



import java.util.HashSet;
import java.util.Set;


import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public abstract class RedisClient {

	private static RedisClient instance = null;

    protected Jedis jedis;
	protected JedisCluster jc;
	protected JedisPool jedisPool;

	public RedisClient() {

	}

	abstract  void init();
	public void initCluster() {



	}
	public void initSingle(){

	}

	public void init2() {

		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(256);// 20
		// config.setMaxWait(5000L);
		config.setMaxWaitMillis(5000l);
		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
		config.setTestWhileIdle(true);
		config.setMinEvictableIdleTimeMillis(60000l);
		config.setTimeBetweenEvictionRunsMillis(3000l);
		config.setNumTestsPerEvictionRun(-1);
		jedisPool = new JedisPool(config, "10.92.1.130", 6379, 60000);
	}

	public Long push(String key, String... value) {
		return jc.rpush(key, value);
	}

	public String pull(String key) {
		return jc.lpop(key);
	}

	public static void main(String[] args) {

		RedisClient client =  null;

		for (int i = 0; i < 1000; i++) {
			client.push("pnr001", String.valueOf(i));
		}

		for (int i = 0; i < 1004; i++) {
			String value = client.pull("pnr001");
			System.out.println("接受到：" + value);
		}

	}

}
