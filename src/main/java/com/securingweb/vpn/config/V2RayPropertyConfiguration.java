package com.securingweb.vpn.config;

import com.securingweb.vpn.entity.property.V2RayProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(V2RayProperties.class)
public class V2RayPropertyConfiguration {
}
