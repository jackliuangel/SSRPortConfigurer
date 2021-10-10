package com.securingweb.vpn.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.ImmutableTag;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MultiTagTracker {
    private final Map<String, Counter> multiTagCounters = new ConcurrentHashMap<>();

    private MeterRegistry registry;

    private String counterName;

    private String[] tags;

    public MultiTagTracker(MeterRegistry registry, String counterName, String... tags) {
        this.registry = registry;
        this.counterName = counterName;
        this.tags = tags;
    }

    public void recordQueryCounts(int count, String... tagsValues) {
        if (tagsValues.length != tags.length)
            throw new RuntimeException("tags and values size must be matched");

        String values = Arrays.toString(tagsValues);

        Counter counter;
        if (multiTagCounters.containsKey(values)) {
            counter = multiTagCounters.get(values);
        } else {
            List<Tag> tagLists = new ArrayList<>(tags.length);
            for (int i = 0; i < tags.length; i++) {
                tagLists.add(new ImmutableTag(tags[i], tagsValues[i]));
            }
            counter = Counter.builder(counterName)
                             .tags(tagLists)
                             .description("multi tag counter " + Arrays.toString(tags))
                             .register(registry);
            multiTagCounters.put(values, counter);
        }

        for (int i = 0; i < count; i++) {
            counter.increment();
        }
    }
}
