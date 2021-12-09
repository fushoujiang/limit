package com.fushoujiang.limit.factory;


import com.fushoujiang.limit.entity.RateLimiterConfig;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

public class RedisRateLimiterFactory extends AbsRateLimiterFactory {

    public static final String PREFIX = "rateLimiter:";
    RedissonClient redissonClient;

    public RedisRateLimiterFactory(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public RateLimiterConfig reLoadLimiterConfig(RateLimiterConfig rateLimiterConfig) {
        String cacheKey = PREFIX + rateLimiterConfig.getProject() + ":" + rateLimiterConfig.getProject();
        final RBucket<String> stringRBucket =  redissonClient.getBucket(cacheKey);
        final String redisConfig = stringRBucket.get();
        if (StringUtils.isNotBlank(redisConfig)){
            rateLimiterConfig = new Gson().fromJson(redisConfig,RateLimiterConfig.class);
        }
        return rateLimiterConfig;
    }




}
