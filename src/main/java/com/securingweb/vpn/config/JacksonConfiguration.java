package com.securingweb.vpn.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Component;

@Component
public class JacksonConfiguration {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new JavaTimeModule());

    public static Jackson2JsonEncoder jackson2JsonEncoder() {
        return new Jackson2JsonEncoder(OBJECT_MAPPER, MediaType.APPLICATION_JSON);
    }

    public static Jackson2JsonDecoder jackson2JsonDecoder() {
        return new Jackson2JsonDecoder(OBJECT_MAPPER, MediaType.APPLICATION_PROBLEM_JSON);
    }


}
