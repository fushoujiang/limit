package com.fushoujiang.limit.limiter;

import java.util.concurrent.TimeUnit;

public class GuavaRateLimiter implements DRateLimiter {

    private com.google.common.util.concurrent.RateLimiter rateLimiter;

    public GuavaRateLimiter(com.google.common.util.concurrent.RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
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
