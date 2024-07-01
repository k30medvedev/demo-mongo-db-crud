package com.example.project.part.filter;

import com.example.project.part.dto.PartResponseDto;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RequestIdCache {

    private static final String REQUEST_ID_PREFIX = "request_id:";
    private static final long TTL = 10; // Time-to-live in minutes

    private final RedisTemplate<String, Object> redisTemplate;

    public RequestIdCache(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isDuplicate(String requestId) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        String key = REQUEST_ID_PREFIX + requestId;
        return ops.get(key) != null;
    }

    public PartResponseDto getCachedResponse(String requestId) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        String key = REQUEST_ID_PREFIX + requestId;
        return (PartResponseDto) ops.get(key);
    }

    public void cacheResponse(String requestId, PartResponseDto response) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        String key = REQUEST_ID_PREFIX + requestId;
        ops.set(key, response, TTL, TimeUnit.MINUTES);
    }
}