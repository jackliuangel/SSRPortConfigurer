package com.securingweb.vpn.service;


import com.securingweb.vpn.entity.V2RayConfig.V2RayServerRootConfig;
import com.securingweb.vpn.entity.VPNConfig;
import com.securingweb.vpn.utility.JsonFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class V2RayPortService implements VPNConfig {
    //    @Value("${V2Ray.Path}")
    @Value("${V2Ray.Path}")
    String path;


    public Integer readPort() throws Exception {
        V2RayServerRootConfig v2RayServerRootConfig = JsonFileUtil.readFromFile(path, V2RayServerRootConfig.class);
        return v2RayServerRootConfig.getInbounds().get(0).getPort();

    }

    public void configPort(Integer newPort) {
        try {
            V2RayServerRootConfig v2RayServerRootConfig = JsonFileUtil.readFromFile(path, V2RayServerRootConfig.class);
            v2RayServerRootConfig.getInbounds().get(0).setPort(newPort);
            JsonFileUtil.writeFile(v2RayServerRootConfig, path);
        } catch (Exception e) {
            log.error("write port config fails with exception {}", e.toString());
        }


    }
}

