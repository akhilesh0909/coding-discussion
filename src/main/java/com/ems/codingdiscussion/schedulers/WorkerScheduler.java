package com.ems.codingdiscussion.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class WorkerScheduler {

    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Autowired
    private Worker worker;

    @Scheduled(fixedDelay = 86400000)
    public void startWorkerThread() {
        executor.execute(worker);
    }

}
