package com.securingweb.vpn.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JacksonConfiguration {
//    //TODO: config mapper
//    @Bean("jacksonObjectMapper")
//    public ObjectMapper JacksonObjectInstance() {
//        return new ObjectMapper();
//    }

    public static ObjectMapper OBJECT_MAPPER = new ObjectMapper();


}
