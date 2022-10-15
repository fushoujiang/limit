package com.fushoujiang.limit.interceptor;

import com.fushoujiang.limit.RateLimitAnnotation;
import com.fushoujiang.limit.RateLimitException;
import com.fushoujiang.limit.entity.RateLimiterConfig;
import com.fushoujiang.limit.factory.GuavaRateLimiterFactory;
import com.fushoujiang.limit.factory.RateLimiterFactory;
import com.fushoujiang.limit.limiter.DRateLimiter;
import com.fushoujiang.limit.manager.ConfigManager;
import com.fushoujiang.limit.manager.LocalConfigManager;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public abstract class AbsRateLimiterInterceptor {

    private RateLimiterFactory rateLimiterFactory;

    private ConfigManager configManager;


    public AbsRateLimiterInterceptor(RateLimiterFactory rateLimiterFactory, ConfigManager configManager) {
        this.rateLimiterFactory = rateLimiterFactory;
        this.configManager = configManager;
    }

    public AbsRateLimiterInterceptor(RateLimiterFactory rateLimiterFactory) {
        this.rateLimiterFactory = rateLimiterFactory;
    }

    public AbsRateLimiterInterceptor(ConfigManager absConfigManager) {
        this.configManager = configManager;
    }

    public AbsRateLimiterInterceptor() {
        rateLimiterFactory = new GuavaRateLimiterFactory();
        configManager = new LocalConfigManager();
    }

    public Object rateLimiterAround(ProceedingJoinPoint point, RateLimitAnnotation annotation) throws Throwable {
        final RateLimiterConfig annotation2RateLimiterConfig = annotation2RateLimiterConfig(annotation);
        final RateLimiterConfig managerRateLimiterConfig = configManager.getRateLimiterConfig(annotation2RateLimiterConfig);
        final DRateLimiter rateLimiter = rateLimiterFactory.getRateLimiter(managerRateLimiterConfig);
        if (managerRateLimiterConfig.isWait()) {
            //阻塞获取令牌
            rateLimiter.acquire();
            return point.proceed();
        }
        //非阻塞获取令牌
        if (rateLimiter.tryAcquire(managerRateLimiterConfig.getTimeOut(), managerRateLimiterConfig.getTimeOutUnit())) {
            return point.proceed();
        }
        if (StringUtils.isNotBlank(managerRateLimiterConfig.getFailBackMethod())) {
            return invokeFallbackMethod(point, managerRateLimiterConfig.getFailBackMethod());
        }
        throw new RateLimitException("【方法】" + point.getSignature().getName() + "【参数】" + Arrays.toString(point.getArgs()) + "调用次数超过" + managerRateLimiterConfig.getPerSecond() + "被限流");
    }


    public abstract RateLimiterConfig annotation2RateLimiterConfig(Annotation annotation);

    protected Method findFallbackMethod(ProceedingJoinPoint joinPoint, String fallbackMethodName) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Class<?>[] parameterTypes = method.getParameterTypes();
        Method fallbackMethod = null;
        try {
            //这里通过判断必须取和原方法一样参数的fallback方法
            fallbackMethod = joinPoint.getTarget().getClass().getMethod(fallbackMethodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return fallbackMethod;
    }


    protected Object invokeFallbackMethod(ProceedingJoinPoint joinPoint, String fallback) {
        Method method = findFallbackMethod(joinPoint, fallback);
        method.setAccessible(true);
        try {
            Object invoke = method.invoke(joinPoint.getTarget(), joinPoint.getArgs());
            return invoke;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RateLimitException(e);
        }
    }
}
