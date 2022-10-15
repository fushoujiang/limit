package com.fushoujiang.limit.factory;

import com.fushoujiang.limit.entity.RateLimiterConfig;
import com.fushoujiang.limit.limiter.DRateLimiter;
import com.fushoujiang.limit.limiter.DRedissonRateLimiter;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;


public class RedissonRateLimiterFactory extends AbsRateLimiterFactory {

    private RedissonClient redisson;

    public RedissonRateLimiterFactory(RedissonClient redisson) {
        this.redisson = redisson;
    }

    @Override
    public DRateLimiter createDRateLimiter(RateLimiterConfig rateLimiterConfig) {
        RRateLimiter rateLimiter =  redisson.getRateLimiter(rateLimiterConfig.getProject() + ":" + rateLimiterConfig.getGroup());
        rateLimiter.setRate(rateLimiterConfig.isCluster() ? RateType.OVERALL : RateType.PER_CLIENT, rateLimiterConfig.getPerSecond(), 1, RateIntervalUnit.SECONDS);
        return new DRedissonRateLimiter(rateLimiter);
    }

}
