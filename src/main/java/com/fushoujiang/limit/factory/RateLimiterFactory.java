package com.fushoujiang.limit.factory;


import com.fushoujiang.limit.entity.RateLimiterConfig;
import com.fushoujiang.limit.entity.WrapConfLimiterEntity;

public interface RateLimiterFactory {

    WrapConfLimiterEntity getWrapConfLimiter(final RateLimiterConfig rateLimiterConfig);

}
