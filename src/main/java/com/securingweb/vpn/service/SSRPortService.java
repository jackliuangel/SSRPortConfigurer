package com.securingweb.vpn.service;


import com.securingweb.vpn.entity.property.SSRProperties;
import com.securingweb.vpn.entity.dto.SSRPortConfig;
import com.securingweb.vpn.entity.dto.VPNConfig;
import com.securingweb.vpn.utility.JsonFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SSRPortService implements VPNConfig {

    @Autowired
    SSRProperties ssrProperties;

    public Integer readPort() throws Exception {

        SSRPortConfig SSRPortConfig = JsonFileUtil.readFromFile(ssrProperties.getPath(), com.securingweb.vpn.entity.dto.SSRPortConfig.class);
        Integer port = SSRPortConfig.getServer_port();
        log.info("read port = {}", port);
        return port;
    }

    public void configPort(Integer newPort) {
        try {
            SSRPortConfig ssrPortConfig = JsonFileUtil.readFromFile(ssrProperties.getPath(), SSRPortConfig.class);
            ssrPortConfig.setServer_port(newPort);

            JsonFileUtil.writeFile(ssrPortConfig, ssrProperties.getPath());
            log.info("write port = {}", ssrPortConfig.getServer_port());
        } catch (Exception e) {
            log.error("write port config fails with exception {}", e.toString());
        }
    }


}

