package com.kun.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    // 配置Redisson客户端
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        // 使用单节点模式，连接到本地Redis服务器
        config.useSingleServer().setAddress("redis://localhost:6379");
        return Redisson.create(config);
    }
}
