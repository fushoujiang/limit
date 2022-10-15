package com.fushoujiang.limit.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.fushoujiang.limit.entity.RateLimiterConfig;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.Properties;
import java.util.concurrent.*;

public class NacosConfigManager extends AbsConfigManager {

    private static final int POOL_SIZE = 1;

    private static final ExecutorService NACOS_UPDATE_CONFIG = new ThreadPoolExecutor(POOL_SIZE, POOL_SIZE, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(), new DefaultThreadFactory("limiter_nacos_listener"));

    private ConfigService configService;

    public NacosConfigManager(ConfigService configService) {
        this.configService = configService;
    }

    private static int TIME_OUT = 3;


    public NacosConfigManager(String serviceAddress) {
        init(serviceAddress);
    }


    @Override
    public RateLimiterConfig loadRateLimiterConfigFromDateSource(RateLimiterConfig rateLimiterConfig) {
        try {
            final String dateId = rateLimiterConfig.getGroup();
            final String group = rateLimiterConfig.getProject();
            String config = configService.getConfigAndSignListener(dateId, group, TIME_OUT, new NacosConfigChangeListener(this, dateId, group));
            return JSON.parseObject(config, RateLimiterConfig.class);
        } catch (NacosException e) {
            e.printStackTrace();
        }
        return rateLimiterConfig;
    }

    private void init(String serviceAddress) {
        Properties properties = new Properties();
        properties.put("serverAddr", serviceAddress);
        try {
            configService = NacosFactory.createConfigService(properties);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    static class NacosConfigChangeListener implements Listener {
        private NacosConfigManager nacosConfigManager;

        private String dataId;
        private String group;

        public NacosConfigChangeListener(NacosConfigManager nacosConfigManager, String dataId, String group) {
            this.nacosConfigManager = nacosConfigManager;
            this.dataId = dataId;
            this.group = group;
        }

        @Override
        public Executor getExecutor() {
            return NACOS_UPDATE_CONFIG;
        }

        @Override
        public void receiveConfigInfo(String configInfo) {
            RateLimiterConfig rateLimiterConfig = JSON.parseObject(configInfo, RateLimiterConfig.class);
            rateLimiterConfig.setProject(group);
            rateLimiterConfig.setGroup(dataId);
            nacosConfigManager.innerGetRateLimiterConfig(rateLimiterConfig, true);
        }
    }
}
