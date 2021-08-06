package com.securingweb.vpn.service;


import com.securingweb.vpn.entity.property.V2RayProperties;
import com.securingweb.vpn.entity.dto.V2RayConfig.V2RayServerRootConfig;
import com.securingweb.vpn.entity.dto.VPNConfig;
import com.securingweb.vpn.utility.JsonFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class V2RayPortService implements VPNConfig {

    @Autowired
    V2RayProperties v2RayProperties;

    public Integer readPort() throws Exception {
        V2RayServerRootConfig v2RayServerRootConfig = JsonFileUtil.readFromFile(v2RayProperties.getPath(), V2RayServerRootConfig.class);
        return v2RayServerRootConfig.getInbounds().get(0).getPort();

    }

    public void configPort(Integer newPort) {
        try {
            V2RayServerRootConfig v2RayServerRootConfig = JsonFileUtil.readFromFile(v2RayProperties.getPath(), V2RayServerRootConfig.class);
            v2RayServerRootConfig.getInbounds().get(0).setPort(newPort);
            JsonFileUtil.writeFile(v2RayServerRootConfig, v2RayProperties.getPath());
        } catch (Exception e) {
            log.error("write port config fails with exception {}", e.toString());
        }


    }
}

