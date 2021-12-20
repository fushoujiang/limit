package com.fushoujiang.limit.factory;

import com.fushoujiang.limit.entity.RateLimiterConfig;
import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/** 
* LocalRateLimiterFactory Tester. 
* 
* @author <Authors name> 
* @since <pre>12月 20, 2021</pre> 
* @version 1.0 
*/ 
public class LocalRateLimiterFactoryTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: reLoadLimiterConfig(RateLimiterConfig rateLimiterConfig) 
* 
*/ 
@Test
public void testReLoadLimiterConfig() throws Exception {
    RateLimiterConfig rateLimiterConfig = new RateLimiterConfig();
    rateLimiterConfig.setGroup("test").setProject("fsj").setPerSecond(50).setWait(true);
    LocalRateLimiterFactory localRateLimiterFactory = new LocalRateLimiterFactory(rateLimiterConfig);
    RateLimiter rateLimiter = localRateLimiterFactory.getWrapConfLimiter(rateLimiterConfig).getRateLimiter();
    Thread.sleep(1000);
    RateLimiter rateLimiter1 = RateLimiter.create(50,2, TimeUnit.SECONDS);
   int s = (int) (System.currentTimeMillis() % 1000);
   Thread.sleep(s);
    for (;;){
        rateLimiter1.acquire();
        System.out.println(new Date());
    }

}


} 
