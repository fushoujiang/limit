package com.fushoujiang.limit.factory;


import com.fushoujiang.limit.entity.RateLimiterConfig;

import java.util.Objects;

public class LocalRateLimiterFactory extends AbsRateLimiterFactory {


    private RateLimiterConfig localRateLimiterConfig;

    public LocalRateLimiterFactory(RateLimiterConfig rateLimiterConfig) {
        this.localRateLimiterConfig = rateLimiterConfig;
    }

    public LocalRateLimiterFactory() {
    }


    @Override
    public RateLimiterConfig reLoadLimiterConfig(RateLimiterConfig rateLimiterConfig) {
        return Objects.isNull(localRateLimiterConfig)?rateLimiterConfig:localRateLimiterConfig;
    }
}
