package com.fushoujiang.limit.interceptor;

import com.fushoujiang.limit.RateLimitAnnotation;
import com.fushoujiang.limit.entity.RateLimiterConfig;
import com.fushoujiang.limit.factory.RateLimiterFactory;
import com.fushoujiang.limit.manager.ConfigManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.annotation.Annotation;


@Aspect
public class RateLimitInterceptor extends AbsRateLimiterInterceptor {

    public RateLimitInterceptor(RateLimiterFactory rateLimiterFactory, ConfigManager configManager) {
        super(rateLimiterFactory, configManager);
    }

    public RateLimitInterceptor(RateLimiterFactory rateLimiterFactory) {
        super(rateLimiterFactory);
    }

    public RateLimitInterceptor(ConfigManager configManager) {
        super(configManager);
    }

    public RateLimitInterceptor() {
    }

    @Around("@annotation(rateLimitAnnotation)")
    public Object around(ProceedingJoinPoint point, RateLimitAnnotation rateLimitAnnotation) throws Throwable {
        return rateLimiterAround(point, rateLimitAnnotation);
    }

    @Override
    public RateLimiterConfig annotation2RateLimiterConfig(Annotation annotation) {
        RateLimitAnnotation rateLimitAnnotation = (RateLimitAnnotation) annotation;
        return new RateLimiterConfig().setCluster(rateLimitAnnotation.cluster())
                .setGroup(rateLimitAnnotation.group())
                .setFailBackMethod(rateLimitAnnotation.failBackMethod())
                .setTimeOut(rateLimitAnnotation.timeOut())
                .setProject(rateLimitAnnotation.project())
                .setTimeOutUnit(rateLimitAnnotation.timeOutUnit())
                .setPerSecond(rateLimitAnnotation.perSecond())
                .setWait(rateLimitAnnotation.isWait());
    }
}
