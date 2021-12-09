package com.fushoujiang.limit.interceptor;

import com.fushoujiang.limit.RateLimitAnnotation;
import com.fushoujiang.limit.entity.RateLimiterConfig;
import com.fushoujiang.limit.entity.WrapConfLimiterEntity;
import com.fushoujiang.limit.factory.RateLimiterFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.annotation.Annotation;


@Aspect
public class RateLimitInterceptor extends AbsRateLimiterInterceptor{

    RateLimiterFactory rateLimiterFactory;

    public RateLimitInterceptor(RateLimiterFactory rateLimiterFactory) {
        this.rateLimiterFactory = rateLimiterFactory;
    }

    @Around("@annotation(rateLimitAnnotation)")
    public Object around(ProceedingJoinPoint point, RateLimitAnnotation rateLimitAnnotation) throws Throwable {
        return rateLimiterAround(point,rateLimitAnnotation);
    }

    @Override
    public WrapConfLimiterEntity rateLimitAnnotation2RateLimiterWithConfig(Annotation annotation) {
        return rateLimiterFactory.getWrapConfLimiter(RateLimiterConfig.rateLimitAnnotation2RateLimiterConfDTO((RateLimitAnnotation)annotation));
    }
}
