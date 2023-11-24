package com.tistory.jaimemin.api.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CouponCountRepository {

    private final static String COUPON_COUNT_KEY = "coupon_count";

    private final RedisTemplate<String, String> redisTemplate;

    public CouponCountRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public long increment() {
        return redisTemplate.opsForValue().increment(COUPON_COUNT_KEY);
    }
}
