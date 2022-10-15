package com.fushoujiang.limit.manager;

import com.alibaba.nacos.client.utils.LogUtils;
import com.fushoujiang.limit.entity.RateLimiterConfig;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class AbsConfigManager implements ConfigManager{

    private static final Logger LOGGER = LogUtils.logger(AbsConfigManager.class);

    private static final ConcurrentMap<String, RateLimiterConfig> REMOTE_CONFIG_CACHE = new ConcurrentHashMap<>();

    /**
     * 从数据源获取配置
     * @param rateLimiterConfig
     * @return RateLimiterConfig
     */
    public abstract RateLimiterConfig loadRateLimiterConfigFromDateSource(RateLimiterConfig rateLimiterConfig);






    private String buildKey(RateLimiterConfig rateLimiterConfig){
        return rateLimiterConfig.getProject()+"_"+rateLimiterConfig.getGroup();
    }
    private void checkRemoteLimiterConfig(RateLimiterConfig local, RateLimiterConfig remote) {
        checkArguments(local);
        checkArguments(remote);
        Preconditions.checkArgument(local.getGroup().equals(remote.getGroup()), "远程加载限流器配置分组group和本地不一致");
        Preconditions.checkArgument(local.getProject().equals(remote.getProject()), "远程加载限流器配置分组project和本地不一致");
    }

    private void checkArguments(RateLimiterConfig rateLimiterConfig) {
        Preconditions.checkArgument(StringUtils.isNotBlank(rateLimiterConfig.getGroup()), "限流器分组group不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(rateLimiterConfig.getProject()), "限流器项目project不能为空");
        Preconditions.checkArgument(rateLimiterConfig.getPerSecond() > 0.0D, "限流器perSecond>0");
    }


    @Override
    public RateLimiterConfig getRateLimiterConfig(RateLimiterConfig rateLimiterConfig) {
       return innerGetRateLimiterConfig(rateLimiterConfig,false);
    }


    public RateLimiterConfig innerGetRateLimiterConfig(RateLimiterConfig rateLimiterConfig,boolean updateCache){
        checkArguments(rateLimiterConfig);
        final String key = buildKey(rateLimiterConfig);
        RateLimiterConfig result = null;
        if (!updateCache){
            result = REMOTE_CONFIG_CACHE.get(key);
        }
        if (Objects.isNull(result)){
            result = loadRateLimiterConfigFromDateSource(rateLimiterConfig);
            checkRemoteLimiterConfig(rateLimiterConfig, result);
            REMOTE_CONFIG_CACHE.put(key,result);
        }
        return result;
    }



}
