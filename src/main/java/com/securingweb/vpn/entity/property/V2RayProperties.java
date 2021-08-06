package com.securingweb.vpn.entity.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "v2ray")
@Getter
@Setter
public class V2RayProperties
{
    private String path;
    private String command;
}