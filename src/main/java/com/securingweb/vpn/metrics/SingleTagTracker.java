package com.securingweb.vpn.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SingleTagTracker {
    private final Map<String, Counter> singleTagCounters = new ConcurrentHashMap<>();

    private MeterRegistry registry;

    private String counterName;

    private String tag;

    public SingleTagTracker(MeterRegistry registry, String counterName, String tag) {
        this.registry = registry;
        this.counterName = counterName;
        this.tag = tag;
    }

    public void recordQueryCounts(int count, String values) {

        Counter counter = singleTagCounters.computeIfAbsent(values, this::newCounter);

        counter.increment(count);
    }

    private Counter newCounter(String value) {
        return Counter.builder(counterName)
                      .tags(tag, value)
                      .description("single tag tracker " + tag)
                      .register(registry);
    }
}
