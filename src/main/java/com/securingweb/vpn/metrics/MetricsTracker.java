package com.securingweb.vpn.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class MetricsTracker {

    private final MultiTagTracker multitagTracker;

    public MetricsTracker(MeterRegistry registry) {
        multitagTracker = new MultiTagTracker(registry, "queryCounter", "uid", "type");
    }

    public void recordQueryCounts(int count, String uid, String type) {
        multitagTracker.recordQueryCounts(count, uid, type);
    }
}
