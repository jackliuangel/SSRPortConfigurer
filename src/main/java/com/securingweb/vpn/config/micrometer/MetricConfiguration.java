package com.securingweb.vpn.config.micrometer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * it generate the dynamic tags
 */
@Configuration
public class MetricConfiguration {

    @Bean
    DefaultWebMvcTagsProvider defaultWebMvcTagsProvider() {
        return new DefaultWebMvcTagsProvider();
    }
}
