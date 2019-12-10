/*
package com.example.srm.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

//注解配置类
@Configuration
//必须加，使配置类生效
//@EnableCaching注解是spring framework中的注解驱动的缓存管理功能。
// 自spring版本3.1起加入了该注解。
// 如果你使用了这个注解，
// 那么你就不需要在XML文件中配置cache manager了，
// 等价于 <cache:annotation-driven/> 。
// 能够在服务类方法上标注@Cacheable
@EnableCaching
public class RedisCOnfiguration extends CachingConfigurerSupport {
    */
/**
     * Logger
     *//*

    private static final Logger lg = LoggerFactory.getLogger(RedisConfiguration.class);

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;


    @Bean
    @Override
    public KeyGenerator keyGenerator() {
//        设置自动key的生成规则，配置spring boot的注解，进行方法级别的缓存
//        使用：进行分割，可以很多现实出层级关系
//        这里其实就是new一个KeyGenerator对象,是Lambda表达式相当于下面这段代码
     */
/*   return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                return null;
            }
        }*//*

        return (target, method, params) -> {
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append(target.getClass().getName());
            stringBuilder.append(":");
            stringBuilder.append(method.getName());
            for(Object obj:params){
                stringBuilder.append(":"+String.valueOf(obj));
            }
            String rsToUse= String.valueOf(stringBuilder);
            lg.info("自动生成Redis Key -> [{}]",rsToUse);
            return rsToUse;
       };
    }

    @Bean
    @Override
    public CacheManager cacheManager() {
//        初始化缓存管理器，在这里我们可以缓存的整体过期时间什么的，我这里默认没有设置
       lg.info("初始化-> [{}]","CacheManager RedisCacheManager Start");
        RedisCacheManager.RedisCacheManagerBuilder builder=RedisCacheManager
                .RedisCacheManagerBuilder
                .fromConnectionFactory(jedisConnectionFactory);
        return builder.build();
    }

    @Bean
    public RedisTemplate<String,Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory){
//        设置序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer=new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om=new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
//        配置redisTemplate
        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        RedisSerializer stringSerializer=new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
//        异常处理，当Redis发生异常时，打印日志,但是程序正常走
        lg.info("初始化 -> [{}]","Redis CacheErrorHandler");
        CacheErrorHandler cacheErrorHandler=new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
                lg.error("redis occur handleCacheGetError: key -> [{}]",key,exception);
            }

            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
                lg.error("Redis occur handleCachePutError: key -> [{}]; value -> [{}]",key,value,exception);
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
                lg.error("Redis occur handleCacheEvictError: key -> [{}]",key,exception);
            }

            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                lg.error("Redis occur handleCacheClearError：",exception);
            }
        };
        return super.errorHandler();
    }
    */
/**
     * 此内部类就是把yml的配置数据，进行读取，创建JedisConnectionFactory和JedisPool，以供外部类初始化缓存管理器使用
     * 不了解的同学可以去看@ConfigurationProperties和@Value的作用
     *
     *//*

    @ConfigurationProperties
    class DataJedisProperties{
        @Value("${spring.redis.host}")
        private String host;

        @Value("${spring.redis.password}")
        private String password;

        @Value("${spring.redis.port}")
        private  int port;

        @Value("${spring.redis.timeout}")
        private int timeout;

        @Value("${spring.redis.jedis.pool.max-idle}")
        private int maxIdle;

        @Value("${spring.redis.jedis.pool.min-idle}")
        private  long maxWaitMillis;


    @Bean
    JedisConnectionFactory redisConnectionFactory(){
        lg.info("Create JedisCOnnectionFactory successful");
        RedisStandaloneConfiguration factory=new RedisStandaloneConfiguration();
        factory.setHostName(host);
        factory.setPort(port);
        factory.setPassword(password);
        return new JedisConnectionFactory(factory);
    }

    @Bean
    public JedisPool redisPoolFactory(){
        lg.info("JedisPool init successful,host -> [{}]",host,port);
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        JedisPool jedisPool =new JedisPool(jedisPoolConfig,host,port,timeout,password);
        return jedisPool;
    }
//
   }
}*/
