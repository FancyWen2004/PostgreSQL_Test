package com.kun.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    // GenericJackson2JsonRedisSerializer()方法是spring-data-redis提供的自带JSON序列化工具
    // 但是它的序列化效率不高，所以我们可以使用fastjson进行序列化。
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        // 创建RedisTemplate对象
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 设置连接工厂
        template.setConnectionFactory(connectionFactory);
        // 创建JSON序列化工具
        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        // 设置Key的序列化
        template.setKeySerializer(RedisSerializer.string());
        template.setHashKeySerializer(RedisSerializer.string());
        // 设置Value的序列化
        template.setValueSerializer(jsonRedisSerializer);
        template.setHashValueSerializer(jsonRedisSerializer);
        return template;
    }

//    // 使用fastjson进行序列化
//    @Bean
//    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory ConnectionFactory) {
//        // 创建RedisTemplate
//        RedisTemplate<Object, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(ConnectionFactory);
//
//        // 使用Fastjson作为值的序列化器
//        GenericFastJsonRedisSerializer fastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
//        template.setValueSerializer(fastJsonRedisSerializer);
//        template.setHashValueSerializer(fastJsonRedisSerializer);
//
//        // 使用 StringRedisSerializer 作为键的序列化器，确保键存储为字符串
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//        template.setKeySerializer(stringRedisSerializer);
//        template.setHashKeySerializer(stringRedisSerializer);
//        return template;
//    }

}
