package com.fushoujiang.limit.limiter;

import java.util.concurrent.TimeUnit;

public interface DRateLimiter {

    boolean tryAcquire(long timeout, TimeUnit unit);

    void acquire();

}
