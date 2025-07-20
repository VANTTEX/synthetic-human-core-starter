package ru.T1Debut.service;

import org.springframework.stereotype.Component;
import ru.T1Debut.annotation.WeylandWatchingYou;
import ru.T1Debut.util.CommandTask;
import ru.T1Debut.exception.QueueOverflowException;

import java.util.concurrent.*;

@Component
public class CommandQueue {

    private final ThreadPoolExecutor executor;

    public CommandQueue() {
        int corePoolSize = 1;
        int maxPoolSize = 1;
        long keepAliveTime = 0;
        BlockingQueue<Runnable> commandQueue = new LinkedBlockingQueue<>(10);

        this.executor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime, TimeUnit.MILLISECONDS,
                commandQueue
        );
    }

    @WeylandWatchingYou
    public void submit(CommandTask task) throws QueueOverflowException {
        try {
            executor.execute(task);
        } catch (RejectedExecutionException e) {
            throw new QueueOverflowException("Очередь переполнена");
        }
    }

    public int getQueueSize() {
        return executor.getQueue().size();
    }
}
