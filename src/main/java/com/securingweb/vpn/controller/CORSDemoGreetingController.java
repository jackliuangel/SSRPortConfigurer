package com.securingweb.vpn.controller;

import com.securingweb.vpn.entity.dto.FeedbackDTO;
import com.securingweb.vpn.entity.dto.Greeting;
import com.securingweb.vpn.metrics.MetricsTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;

@RestController
public class CORSDemoGreetingController {

    private static final String template = "Hello, %s!";

    private final AtomicLong counter = new AtomicLong();

    @Autowired
    MetricsTracker metricsTracker;

    @CrossOrigin
    @GetMapping("/cors")
    public Greeting greeting(@RequestParam(required = false, defaultValue = "World") String name) {
        metricsTracker.recordQueryCounts(1, "anonymous", "cors");
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @PostMapping(value = "/feedback", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, APPLICATION_OCTET_STREAM_VALUE})
    public Greeting feedback(@RequestPart("attachments") List<MultipartFile> attachments,
                             @RequestPart("feedbackDTO") FeedbackDTO feedbackDTO) {
        String content = String.format("%s attachments are received, DTO is %s", attachments, feedbackDTO.toString());
        metricsTracker.recordQueryCounts(1, "anonymous", "feedback");
        return new Greeting(counter.incrementAndGet(), content);
    }
}
