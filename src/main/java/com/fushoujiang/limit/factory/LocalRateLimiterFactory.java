package com.fushoujiang.limit.factory;


import com.fushoujiang.limit.entity.RateLimiterConfig;

public class LocalRateLimiterFactory extends AbsRateLimiterFactory {

    @Override
    public RateLimiterConfig reLoadLimiterConfig(RateLimiterConfig rateLimiterConfig) {
        return rateLimiterConfig;
    }
}
