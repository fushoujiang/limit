package com.fushoujiang.limit.factory;


import com.fushoujiang.limit.entity.RateLimiterConfig;
import com.fushoujiang.limit.entity.WrapConfLimiterEntity;
import com.fushoujiang.limit.limiter.DRateLimiter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class AbsRateLimiterFactory implements RateLimiterFactory {
    private static final ConcurrentMap<String, WrapConfLimiterEntity> RATE_LIMITER_CACHE = new ConcurrentHashMap<>();


    @Override
    public DRateLimiter getRateLimiter(RateLimiterConfig rateLimiterConfig) {
        final String cacheKey = buildCacheKey(rateLimiterConfig);

        WrapConfLimiterEntity wrapConfLimiterEntity = getCache(cacheKey);

        if (needUpdateCache(wrapConfLimiterEntity, rateLimiterConfig)) {
            DRateLimiter rateLimiter = createDRateLimiter(rateLimiterConfig);
            putCache(cacheKey, wrapConfLimiterEntity = new WrapConfLimiterEntity(rateLimiterConfig, rateLimiter));
        }
        return wrapConfLimiterEntity.getRateLimiter();
    }


    public abstract DRateLimiter createDRateLimiter(RateLimiterConfig rateLimiterConfig);


    private WrapConfLimiterEntity getCache(final String key) {
        return RATE_LIMITER_CACHE.get(key);
    }

    private WrapConfLimiterEntity putCache(final String key, final WrapConfLimiterEntity wrapConfLimiter) {
        return RATE_LIMITER_CACHE.put(key, wrapConfLimiter);
    }

    private boolean needUpdateCache(WrapConfLimiterEntity wrapConfLimiter, RateLimiterConfig rateLimiterConfig) {
        if (wrapConfLimiter == null) return true;
        return !wrapConfLimiter.getRateLimiterConfig().equals(rateLimiterConfig);
    }

    private String buildCacheKey(RateLimiterConfig rateLimiterConfig) {
        return rateLimiterConfig.getProject() + "_" + rateLimiterConfig.getGroup();
    }


}
