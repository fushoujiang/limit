package com.fushoujiang.limit.manager;

import com.alibaba.fastjson.JSON;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.fushoujiang.limit.entity.RateLimiterConfig;
import io.netty.util.concurrent.DefaultThreadFactory;
import jdk.internal.joptsimple.internal.Strings;


public class ApolloConfigManager extends AbsConfigManager {



    private Config config;


    public ApolloConfigManager(Config config) {
        this.config = config;
        config.addChangeListener(new ApolloConfigChangeListener(this));
    }

    @Override
    public RateLimiterConfig loadRateLimiterConfigFromDateSource(RateLimiterConfig rateLimiterConfig) {
        final String key = rateLimiterConfig.getProject() + "_" + rateLimiterConfig.getGroup();
        String configProperty = config.getProperty(key, "");
        if (Strings.isNullOrEmpty(configProperty)) return rateLimiterConfig;
        return JSON.parseObject(configProperty, RateLimiterConfig.class);
    }


    static class ApolloConfigChangeListener implements ConfigChangeListener {
        ApolloConfigManager apolloConfigManager;

        public ApolloConfigChangeListener(ApolloConfigManager apolloConfigManager) {
            this.apolloConfigManager = apolloConfigManager;
        }

        @Override
        public void onChange(ConfigChangeEvent configChangeEvent) {
            for (String key : configChangeEvent.changedKeys()) {
                ConfigChange change = configChangeEvent.getChange(key);
                RateLimiterConfig rateLimiterConfig = JSON.parseObject(change.getNewValue(), RateLimiterConfig.class);
                String[] split = key.split("_");
                rateLimiterConfig.setProject(split[0]);
                rateLimiterConfig.setGroup(split[1]);
                apolloConfigManager.innerGetRateLimiterConfig(rateLimiterConfig, true);
            }

        }
    }


}
