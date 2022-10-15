package com.fushoujiang.limit.factory;

import com.fushoujiang.limit.entity.RateLimiterConfig;
import com.fushoujiang.limit.limiter.DRateLimiter;


public interface RateLimiterFactory {

    DRateLimiter getRateLimiter(final RateLimiterConfig rateLimiterConfig);

}
