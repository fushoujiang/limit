package com.fushoujiang.limit.manager;


import com.fushoujiang.limit.entity.RateLimiterConfig;

public class LocalConfigManager extends AbsConfigManager {
    @Override
    public RateLimiterConfig loadRateLimiterConfigFromDateSource(RateLimiterConfig rateLimiterConfig) {
        return rateLimiterConfig;
    }


}
