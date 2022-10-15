package com.fushoujiang.limit.entity;


import com.fushoujiang.limit.limiter.DRateLimiter;

public class WrapConfLimiterEntity {

    private RateLimiterConfig rateLimiterConfig;
    private DRateLimiter rateLimiter;

    public WrapConfLimiterEntity(RateLimiterConfig rateLimiterConfig, DRateLimiter rateLimiter) {
        this.rateLimiterConfig = rateLimiterConfig;
        this.rateLimiter = rateLimiter;
    }

    public RateLimiterConfig getRateLimiterConfig() {
        return rateLimiterConfig;
    }

    public WrapConfLimiterEntity setRateLimiterConfig(RateLimiterConfig rateLimiterConfig) {
        this.rateLimiterConfig = rateLimiterConfig;
        return this;
    }

    public DRateLimiter getRateLimiter() {
        return rateLimiter;
    }

    public void setRateLimiter(DRateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }
}
