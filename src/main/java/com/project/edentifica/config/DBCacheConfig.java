package com.project.edentifica.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;

@Configuration
@EnableCaching
public class DBCacheConfig {

    public static final String CACHE_USER ="user";
    public static final String CACHE_PROFILE ="profile";


    /**
     * Configures and initializes a Redisson client.
     * This client is used for caching database queries and results in Redis.
     *
     * @return RedissonClient instance
     */
    @Bean(destroyMethod = "shutdown")//this frees the resources when the bean is destroyed
    public RedissonClient redissonClient(){
      var config = new Config();
      config.useSingleServer().
              setAddress("redis://127.0.0.1:6379");

      return Redisson.create(config);
    }

    /**
     * Configures and initializes a CacheManager for Redisson.
     * This CacheManager handles caching of database query results using Redis.
     *
     * @param redissonClient The Redisson client instance to be used for caching.
     * @return CacheManager instance
     */
    @Bean
    @Autowired
    public CacheManager cacheManager(RedissonClient redissonClient){
        var configMap = new HashMap<String, CacheConfig>();
        configMap.put(CACHE_USER, new CacheConfig());
        configMap.put(CACHE_PROFILE, new CacheConfig());

        return new RedissonSpringCacheManager(redissonClient, configMap);
    }

}
