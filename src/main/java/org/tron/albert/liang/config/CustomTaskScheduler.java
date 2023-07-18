package org.tron.albert.liang.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomTaskScheduler extends ThreadPoolTaskScheduler {



    private static final int thread= Runtime.getRuntime().availableProcessors();

    public CustomTaskScheduler() {
        this.setPoolSize(thread*10);
        this.setThreadNamePrefix("scheduler-task-");
        this.setErrorHandler(t -> log.error("Exception in Scheduled task. ", t));
        this.setAwaitTerminationSeconds(60);
        this.setWaitForTasksToCompleteOnShutdown(true);
    }
}
