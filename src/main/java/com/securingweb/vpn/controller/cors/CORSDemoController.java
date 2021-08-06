package com.securingweb.vpn.controller.cors;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class CORSDemoController {

    private static final String template = "Hello, %s!";

    private final AtomicLong counter = new AtomicLong();

    @CrossOrigin
    @GetMapping("/cors")
    public Greeting greeting(@RequestParam(required = false, defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
