package com.kun.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

// 使用Redis生成全局唯一ID
@Component
public class RedisIdWorker {

    // 假设BEGIN_TIMESTAMP是起始时间戳，这里先随意给定一个示例值，实际应根据业务需求确定
    private static final long BEGIN_TIMESTAMP = 1609459200L;

    // 假设COUNT_BITS是用于位移操作的位数，这里先给定一个示例值，实际应根据业务需求确定
    private static final int COUNT_BITS = 32;

    private final StringRedisTemplate stringRedisTemplate;

    // 构造函数注入StringRedisTemplate
    public RedisIdWorker(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public long nextId(String keyPrefix) {

        // 1.生成时间戳
        LocalDateTime now = LocalDateTime.now();
        long nowSecond = now.toEpochSecond(ZoneOffset.UTC);
        long timestamp = nowSecond - BEGIN_TIMESTAMP;

        // 2.生成序列号
        // 2.1.获取当前日期，精确到天
        String date = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));

        // 2.2.自增长，设置过期时间为1min
        // 这里使用Redis的increment方法，key为"secKill:keyPrefix:date"，value为自增长的序列号
        long count = stringRedisTemplate.opsForValue().increment("secKill:" + keyPrefix + ":" + date);

        // 3.使用位运算生成全局唯一ID
        // 3.1.左移32位，将时间戳左移32位
        // 3.2.按位或，将序列号与时间戳进行按位或操作，得到全局唯一ID
        return timestamp << COUNT_BITS | count;
    }
}