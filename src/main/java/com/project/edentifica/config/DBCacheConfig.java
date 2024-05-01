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
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;

@Configuration
@EnableCaching
public class DBCacheConfig {

    public static final String CACHE_USER ="user";
    public static final String CACHE_PROFILE ="profile";
    public static final String CACHE_EMAIL ="email";
    public static final String CACHE_PHONE ="phone";
    public static final String CACHE_SOCIAL_NETWORK ="socialNetwork";
    public static final String CACHE_MATHEMATICAL_CHALLENGE ="mathematicalChallenges";


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

        // Configura la duración de la caché en 24 horas (en milisegundos)
        // Set cache duration in 24 hours (in milliseconds)
        long cacheExpiration = 24 * 60 * 60 * 1000; // 24 hours in milliseconds

        // Set CacheConfig for CACHE_USER
        CacheConfig cacheConfigUser = new CacheConfig();
        cacheConfigUser.setMaxSize(10000);
        cacheConfigUser.setTTL(cacheExpiration);
        configMap.put(CACHE_USER, cacheConfigUser);

        // Set CacheConfig for CACHE_PROFILE
        CacheConfig cacheConfigProfile = new CacheConfig();
        cacheConfigProfile.setMaxSize(10000);
        cacheConfigProfile.setTTL(cacheExpiration);
        configMap.put(CACHE_PROFILE, cacheConfigProfile);

        // Set CacheConfig for CACHE_EMAIL
        CacheConfig cacheConfigEmail = new CacheConfig();
        cacheConfigProfile.setMaxSize(10000);
        cacheConfigProfile.setTTL(cacheExpiration);
        configMap.put(CACHE_EMAIL, cacheConfigEmail);

        // Set CacheConfig for CACHE_PHONE
        CacheConfig cacheConfigPhone = new CacheConfig();
        cacheConfigProfile.setMaxSize(10000);
        cacheConfigProfile.setTTL(cacheExpiration);
        configMap.put(CACHE_PHONE, cacheConfigPhone);

        // Set CacheConfig for CACHE_SOCIAL_NETWORK
        CacheConfig cacheConfigSocialNetwork = new CacheConfig();
        cacheConfigProfile.setMaxSize(10000);
        cacheConfigProfile.setTTL(cacheExpiration);
        configMap.put(CACHE_SOCIAL_NETWORK, cacheConfigSocialNetwork);

        // Set CacheConfig for CACHE_MATHEMATICAL_CHALLENGE
        CacheConfig cacheConfigMathematicalChallenge = new CacheConfig();
        cacheConfigProfile.setMaxSize(10000);
        cacheConfigProfile.setTTL(cacheExpiration);
        configMap.put(CACHE_MATHEMATICAL_CHALLENGE, cacheConfigMathematicalChallenge);

        return new RedissonSpringCacheManager(redissonClient, configMap);
    }

    //RestTemplate que se utiliza para el servidor de llamadas desde la clase del servicio "CallService"
    //RestTemplate used for the call server from the service class “CallService”.
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
