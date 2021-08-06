package com.securingweb.vpn.config;

import com.securingweb.vpn.entity.property.SSRProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SSRProperties.class)
public class SSRPropertyConfiguration
{
}
