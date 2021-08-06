package com.securingweb.vpn.entity.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ssr")
@Getter
@Setter
public class SSRProperties
{
    private String path;
    private String command;
}