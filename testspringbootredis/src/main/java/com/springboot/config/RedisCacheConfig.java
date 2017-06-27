package com.springboot.config;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
    /**
     * �������ע���ã�
     * @author Administrator
     *
     */
    @Configuration
    @EnableCaching
    public class RedisCacheConfig  extends CachingConfigurerSupport {
            
        /**
     * ����key�Ĳ���
     *
     * @return
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }
    
    /**
     * ������
     *
     * @param redisTemplate
     * @return
     */
    @SuppressWarnings("rawtypes")
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
        //���û������ʱ��
        // rcm.setDefaultExpiration(60);//��
        //����value�Ĺ���ʱ��
       // Map<String,Long> map=new HashMap();
       // map.put("test",60L);
       // rcm.setExpires(map);
        return rcm;
    }
    
    /**
     * RedisTemplate����
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);//���key��String ��Ҫ����һ��StringSerializer,��Ȼkey������ /XX/XX
        template.afterPropertiesSet();
        return template;
    }
    
    @Bean  
    @Override  
    public CacheErrorHandler errorHandler() {  
        CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {  
//            public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {  
//                System.out.println(key);  
//            }  
//  
//            public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {  
//                System.out.println(value);  
//            }  
//  
//            public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {  
//  
//            }  
//  
//            public void handleCacheClearError(RuntimeException e, Cache cache) {  
//  
//            }

			public void handleCacheGetError(RuntimeException exception,
					Cache cache, Object key) {
				// TODO Auto-generated method stub
				 System.out.println(key);  
			}

			public void handleCachePutError(RuntimeException exception,
					Cache cache, Object key, Object value) {
				// TODO Auto-generated method stub
				 System.out.println(key);  
			}

			public void handleCacheEvictError(RuntimeException exception,
					Cache cache, Object key) {
				// TODO Auto-generated method stub
				
			}

			public void handleCacheClearError(RuntimeException exception,
					Cache cache) {
				// TODO Auto-generated method stub
				
			}  
        };  
        return cacheErrorHandler;  
    }  
}