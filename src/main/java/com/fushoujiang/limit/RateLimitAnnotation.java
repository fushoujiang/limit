package com.fushoujiang.limit;


import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @see com.fushoujiang.limit.interceptor.RateLimitInterceptor
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimitAnnotation {
    /**
     * 限流项目名称
     *
     * @return
     */
    String project() default "default_project_name";

    /**
     * 限流的分组
     *
     * @return
     */
    String group() default "default_group_name";

    /**
     * <p>
     * true：com.daojia.gunpowder.feature.limit.limiter.DRateLimiter#acquire()
     * false：com.daojia.gunpowder.feature.limit.limiter.DRateLimiter#tryAcquire(long, java.util.concurrent.TimeUnit)
     * </p>
     *
     * @return boolean
     */
    boolean isWait() default false;

    /**
     * 每秒向桶中放入令牌的数量   默认最大即不做限流
     *
     * @return int
     */
    int perSecond() default Integer.MAX_VALUE;

    /**
     * 是否是集群模式
     * <p>
     * 限流器必须支持集群模式
     * </p>
     *
     * @return boolean
     */
    boolean cluster() default false;

    /**
     * 失败之后执行本类的方法名,若无则抛异常,强烈建议限流->降级
     * <p>
     * 入参和返回值和增强方法需保持一致
     * </p>
     */
    String failBackMethod() default "";

    /**
     * 获取令牌的等待时间  默认0
     *
     * @return int
     */
    int timeOut() default 0;

    /**
     * 超时时间单位
     *
     * @return TimeUnit
     */
    TimeUnit timeOutUnit() default TimeUnit.SECONDS;

}