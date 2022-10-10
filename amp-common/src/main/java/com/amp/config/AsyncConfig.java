package com.amp.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 *
 * 设置线程池执行异步
 * @author guoyu
 * @version 1.0
 * @date 2021/4/29 2:39 下午
 */
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
    private static final int MAX_POOL_SIZE = 50;

    private static final int CORE_POOL_SIZE = 20;

    private static final int QUEUE_NUMBER = 100;


    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        taskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        taskExecutor.setQueueCapacity(QUEUE_NUMBER);
        taskExecutor.setThreadNamePrefix("async-task-thread-pool");
        taskExecutor.initialize();
        return taskExecutor;
    }

    // 用于捕获异步异常
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }
}
