package com.fushoujiang.limit.factory;


import com.fushoujiang.limit.entity.RateLimiterConfig;
import com.fushoujiang.limit.limiter.DRateLimiter;
import com.fushoujiang.limit.limiter.GuavaRateLimiter;
import com.google.common.util.concurrent.RateLimiter;

public class GuavaRateLimiterFactory extends AbsRateLimiterFactory {


    public DRateLimiter createDRateLimiter(RateLimiterConfig rateLimiterConfig) {
        return new GuavaRateLimiter(RateLimiter.create(rateLimiterConfig.getPerSecond()));
    }
}
