package com.fushoujiang.limit.entity;



import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class RateLimiterConfig {
    /**
     * 限流的集群
     *
     * @return
     */
    private String project;
    /**
     * 限流的分组
     *
     * @return
     */
    private String group;

    /**
     * 是否抛弃请求
     */
    private boolean wait;

    /**
     * 每秒向桶中放入令牌的数量   默认最大即不做限流
     *
     * @return
     */
    private int perSecond = Integer.MAX_VALUE;

    /**
     * 获取令牌的等待时间  默认0
     * isWait()==false生效
     *
     * @return
     */
    private int timeOut;
    /**
     * 超时时间单位
     *
     * @return
     */
    private TimeUnit timeOutUnit = TimeUnit.SECONDS;

    /**
     * 失败之后执行本类的方法名,若无则抛异常
     * <p>
     * 入参和返回值和增强方法需保持一致
     * </p>
     */
    private String failBackMethod;

    /**
     * 是否是集群模式
     */
    private boolean cluster;


    public boolean isCluster() {
        return cluster;
    }

    public RateLimiterConfig setCluster(boolean cluster) {
        this.cluster = cluster;
        return this;
    }

    public String getProject() {
        return project;
    }

    public RateLimiterConfig setProject(String project) {
        this.project = project;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public RateLimiterConfig setGroup(String group) {
        this.group = group;
        return this;
    }

    public boolean isWait() {
        return wait;
    }

    public RateLimiterConfig setWait(boolean wait) {
        this.wait = wait;
        return this;
    }

    public int getPerSecond() {
        return perSecond;
    }

    public RateLimiterConfig setPerSecond(int perSecond) {
        this.perSecond = perSecond;
        return this;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public RateLimiterConfig setTimeOut(int timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    public TimeUnit getTimeOutUnit() {
        return timeOutUnit;
    }

    public RateLimiterConfig setTimeOutUnit(TimeUnit timeOutUnit) {
        this.timeOutUnit = timeOutUnit;
        return this;
    }

    public String getFailBackMethod() {
        return failBackMethod;
    }

    public RateLimiterConfig setFailBackMethod(String failBackMethod) {
        this.failBackMethod = failBackMethod;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RateLimiterConfig that = (RateLimiterConfig) o;
        if (wait != that.wait) return false;
        if (perSecond != that.perSecond) return false;
        if (timeOut != that.timeOut) return false;
        if (!Objects.equals(project, that.project)) return false;
        if (!Objects.equals(group, that.group)) return false;
        if (timeOutUnit != that.timeOutUnit) return false;
        if (cluster != that.cluster) return false;
        return Objects.equals(failBackMethod, that.failBackMethod);
    }

    @Override
    public int hashCode() {
        int result = project != null ? project.hashCode() : 0;
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (wait ? 1 : 0);
        result = 31 * result + perSecond;
        result = 31 * result + timeOut;
        result = 31 * result + (timeOutUnit != null ? timeOutUnit.hashCode() : 0);
        result = 31 * result + (failBackMethod != null ? failBackMethod.hashCode() : 0);
        return result;
    }
}
