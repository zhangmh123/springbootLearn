package com.springboot.config;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.OxmSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.springboot.bean.User;
  
/** 
 * @author Jon Brisbin 
 */  
@Configuration  
public abstract class ApplicationConfig {  
  
    @Bean(name="connectionFactory")  
    public RedisConnectionFactory redisConnectionFactory() {  
        JedisConnectionFactory cf = new JedisConnectionFactory();  
        cf.setHostName("172.29.50.32");  
        cf.setPort(6379);  
        //cf.setPassword("superman");  
        cf.afterPropertiesSet();  
        return cf;  
    }  
  
    @Bean  
    public RedisTemplate redisTemplate() {  
        RedisTemplate rt = new RedisTemplate();  
        rt.setConnectionFactory(redisConnectionFactory());  
        return rt;  
    }  
  
    private static Map<Class, JAXBContext> jaxbContextHashMap = new ConcurrentHashMap<Class, JAXBContext>();  
  
    @Bean  
    public OxmSerializer oxmSerializer() throws Throwable{  
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();  
        Map<String,Object> properties = new HashMap<String, Object>();//创建映射，用于设置Marshaller属性  
        properties.put(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);   //放置xml自动缩进属性  
        properties.put(Marshaller.JAXB_ENCODING,"utf-8");   //放置xml自动缩进属性  
        jaxb2Marshaller.setClassesToBeBound(User.class);//映射的xml类放入JAXB环境中  
        jaxb2Marshaller.setMarshallerProperties(properties);//设置Marshaller属性  
        return  new OxmSerializer(jaxb2Marshaller,jaxb2Marshaller);  
    }  
  
    public static enum StringSerializer implements RedisSerializer<String> {  
        INSTANCE;  
  
        public byte[] serialize(String s) throws SerializationException {  
            return (null != s ? s.getBytes() : new byte[0]);  
        }  
  
        public String deserialize(byte[] bytes) throws SerializationException {  
            if (bytes.length > 0) {  
                return new String(bytes);  
            } else {  
                return null;  
            }  
        }  
    }  
  
    public static enum LongSerializer implements RedisSerializer<Long> {  
        INSTANCE;  
  
        public byte[] serialize(Long aLong) throws SerializationException {  
            if (null != aLong) {  
                return aLong.toString().getBytes();  
            } else {  
                return new byte[0];  
            }  
        }  
  
        public Long deserialize(byte[] bytes) throws SerializationException {  
            if (bytes.length > 0) {  
                return Long.parseLong(new String(bytes));  
            } else {  
                return null;  
            }  
        }  
    }  
  
  
    public static enum IntSerializer implements RedisSerializer<Integer> {  
        INSTANCE;  
  
        public byte[] serialize(Integer i) throws SerializationException {  
            if (null != i) {  
                return i.toString().getBytes();  
            } else {  
                return new byte[0];  
            }  
        }  
  
        public Integer deserialize(byte[] bytes) throws SerializationException {  
            if (bytes.length > 0) {  
                return Integer.parseInt(new String(bytes));  
            } else {  
                return null;  
            }  
        }  
    }  
  
}  