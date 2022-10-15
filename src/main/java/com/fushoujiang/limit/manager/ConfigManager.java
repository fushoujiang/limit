package com.fushoujiang.limit.manager;

import com.fushoujiang.limit.entity.RateLimiterConfig;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

public interface  ConfigManager {


    default RateLimiterConfig getRateLimiterConfig(RateLimiterConfig rateLimiterConfig) {
        checkArguments(rateLimiterConfig);
        RateLimiterConfig configFromDateSource = loadRateLimiterConfigFromDateSource(rateLimiterConfig);
        checkRemoteLimiterConfig(rateLimiterConfig, configFromDateSource);
        return configFromDateSource;
    }

    RateLimiterConfig loadRateLimiterConfigFromDateSource(RateLimiterConfig rateLimiterConfig);

    default void checkRemoteLimiterConfig(RateLimiterConfig local, RateLimiterConfig remote) {
        checkArguments(local);
        checkArguments(remote);
        Preconditions.checkArgument(local.getGroup().equals(remote.getGroup()), "远程加载限流器配置分组group和本地不一致");
        Preconditions.checkArgument(local.getProject().equals(remote.getProject()), "远程加载限流器配置分组project和本地不一致");
    }

    default void checkArguments(RateLimiterConfig rateLimiterConfig) {
        Preconditions.checkArgument(StringUtils.isNotBlank(rateLimiterConfig.getGroup()), "限流器分组group不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(rateLimiterConfig.getProject()), "限流器项目project不能为空");
        Preconditions.checkArgument(rateLimiterConfig.getPerSecond() > 0.0D, "限流器perSecond>0");
    }

}
