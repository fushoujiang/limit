package com.fushoujiang.limit.manager;

import com.fushoujiang.limit.entity.RateLimiterConfig;

public interface  ConfigManager {



    RateLimiterConfig getRateLimiterConfig(RateLimiterConfig rateLimiterConfig);

}
