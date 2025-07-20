package ru.T1Debut.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TaskMetrics {

    private final MeterRegistry registry;
    private final Map<String, Counter> counters = new ConcurrentHashMap<>();

    public TaskMetrics(MeterRegistry registry, CommandQueue commandQueue) {
        this.registry = registry;

        Gauge.builder("commands.in.queue", commandQueue, CommandQueue::getQueueSize)
                .description("Текущее количество задач в очереди")
                .register(registry);
    }

    public void registerCompletedByAuthor(String author) {
        counters.computeIfAbsent(author, a ->
                registry.counter("commands.completed.by.author", "author", a)
        ).increment();
    }
}
