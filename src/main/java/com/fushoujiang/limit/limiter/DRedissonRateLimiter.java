package com.fushoujiang.limit.limiter;

import org.redisson.api.RRateLimiter;

import java.util.concurrent.TimeUnit;

public class DRedissonRateLimiter implements DRateLimiter {

    private RRateLimiter rateLimiter ;

    public DRedissonRateLimiter(RRateLimiter redissonRateLimiter) {
        this.rateLimiter = redissonRateLimiter;
    }

    @Override
    public boolean tryAcquire(long timeout, TimeUnit unit) {
        return rateLimiter.tryAcquire(timeout,unit);
    }

    @Override
    public void acquire() {
        rateLimiter.acquire();
    }
}
