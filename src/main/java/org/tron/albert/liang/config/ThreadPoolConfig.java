package org.tron.albert.liang.config;

/**
 * @author tieJiang
 * @date 2023/6/29 20:58
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolConfig {

    @Bean(name = "threadPoolTaskExecutor")
    public static ThreadPoolTaskExecutor analyzeTradeThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(140);
        executor.setMaxPoolSize(280);
        executor.setQueueCapacity(6000);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("TASK");
        return executor;
    }


}
