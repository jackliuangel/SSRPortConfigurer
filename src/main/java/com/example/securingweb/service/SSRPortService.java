package com.example.securingweb.service;


import com.example.securingweb.entity.SSRPortConfig;
import com.example.securingweb.entity.VPNConfig;
import com.example.securingweb.utility.JsonFileUtil;
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
        SSRPortConfig SSRPortConfig = JsonFileUtil.readFromFile(path, com.example.securingweb.entity.SSRPortConfig.class);
        Integer port = SSRPortConfig.getServer_port();
        log.info("read port = {}", port);
        return port;
    }

    public void configPort(Integer newPort) {
        try {
            SSRPortConfig ssrPortConfig = JsonFileUtil.readFromFile(path, com.example.securingweb.entity.SSRPortConfig.class);
            ssrPortConfig.setServer_port(newPort);

            JsonFileUtil.writeFile(ssrPortConfig, path);
            log.info("write port = {}", ssrPortConfig.getServer_port());
        } catch (Exception e) {
            log.error("write port config fails with exception {}", e.toString());
        }
    }


}

