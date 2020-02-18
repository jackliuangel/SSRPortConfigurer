package com.example.securingweb.service;


import com.example.securingweb.entity.PortConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

@Component
@Slf4j
public class SSRPortService {
    @Value("${SSR.Path}")
    String path;


    public Integer readPort() throws Exception {
        PortConfig portConfig = readFromFile();
        return portConfig.getServer_port();

    }

    public void configPort(Integer newPort) throws Exception {
        PortConfig portConfig = readFromFile();
        portConfig.setServer_port(newPort);
        writeFile(portConfig);


    }


    private PortConfig readFromFile() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        // Read JSON file and convert to java object
        InputStream fileInputStream = new FileInputStream(path);
        PortConfig portConfig = mapper.readValue(fileInputStream, PortConfig.class);
        fileInputStream.close();
        return portConfig;

    }


    private void writeFile(PortConfig newPortConfig) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        String postJson = mapper.writeValueAsString(newPortConfig);
        log.info("new config is {}", postJson);

        // Save JSON string to file
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(fileOutputStream, newPortConfig);
        fileOutputStream.close();
    }
}

