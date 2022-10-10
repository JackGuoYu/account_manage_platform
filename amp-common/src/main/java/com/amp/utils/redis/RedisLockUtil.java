package com.amp.utils.redis;


import com.amp.exception.AmpException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁
 */
@Slf4j
@Data
@Component
public class RedisLockUtil {

    @Autowired
    private RedissonClient redissonClient;

    public static class RLockContainer implements AutoCloseable {
        private RLock lock;

        public RLock getLock() {
            return lock;
        }

        public RLockContainer(RLock lock) {
            this.lock = lock;
        }

        @Override
        public void close() {
            if (lock != null) {
                lock.unlock();
            }
        }

        public void tryLock(long time, TimeUnit unit) {
            boolean flag;
            try {
                flag = lock.tryLock(time, unit);
            } catch (InterruptedException e) {
                log.error("获取不到锁 ");
                throw new AmpException(e.getMessage());
            }
            if (!flag) {
                log.warn("redis分布锁获取不到锁， 操作频繁");
                throw new AmpException("操作频繁");
            }
        }

        /**
         * 分布式锁 true 获取锁 false 未获取锁
         *
         * @param waitTime 等待时间
         * @param leaseTime 锁时间
         * @param unit 单位
         * @author Emil.Wu
         * @date 2021-12-20 14:14
         * @version 1.0
         * @return true 获取锁 false 未获取锁
         */
        public boolean attemptLock(long waitTime,long leaseTime, TimeUnit unit){
            try {
                return lock.tryLock(waitTime,leaseTime, unit);
            } catch (Exception e) {
                log.warn("获取不到锁,err:{}",e);
                return false;
            }
        }
    }


    public RLock getLock(String lockKey) {
        return redissonClient.getLock(lockKey);
    }

    public RLockContainer getLockContainer(String lockKey) {
        return new RLockContainer(getLock(lockKey));
    }


}
