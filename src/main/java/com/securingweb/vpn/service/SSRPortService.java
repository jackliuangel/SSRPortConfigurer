package com.securingweb.vpn.service;


import com.securingweb.vpn.entity.SSRPortConfig;
import com.securingweb.vpn.entity.VPNConfig;
import com.securingweb.vpn.utility.JsonFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SSRPortService implements VPNConfig {


    //    @Value("${SSR.Path}")
    @Value("${SSR.Path}")
    String path;


    public Integer readPort() throws Exception {

        SSRPortConfig SSRPortConfig = JsonFileUtil.readFromFile(path, com.securingweb.vpn.entity.SSRPortConfig.class);
        Integer port = SSRPortConfig.getServer_port();
        log.info("read port = {}", port);
        return port;
    }

    public void configPort(Integer newPort) {
        try {
            SSRPortConfig ssrPortConfig = JsonFileUtil.readFromFile(path, SSRPortConfig.class);
            ssrPortConfig.setServer_port(newPort);

            JsonFileUtil.writeFile(ssrPortConfig, path);
            log.info("write port = {}", ssrPortConfig.getServer_port());
        } catch (Exception e) {
            log.error("write port config fails with exception {}", e.toString());
        }
    }


}

