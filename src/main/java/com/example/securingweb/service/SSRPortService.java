package com.example.securingweb.service;


import com.example.securingweb.entity.SSRPortConfig;
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
        SSRPortConfig SSRPortConfig = readFromFile();
        return SSRPortConfig.getServer_port();

    }

    public void configPort(Integer newPort) throws Exception {
        SSRPortConfig SSRPortConfig = readFromFile();
        SSRPortConfig.setServer_port(newPort);
        writeFile(SSRPortConfig);


    }


    private SSRPortConfig readFromFile() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        // Read JSON file and convert to java object
        InputStream fileInputStream = new FileInputStream(path);
        SSRPortConfig SSRPortConfig = mapper.readValue(fileInputStream, SSRPortConfig.class);
        fileInputStream.close();
        return SSRPortConfig;

    }


    private void writeFile(SSRPortConfig newSSRPortConfig) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        String postJson = mapper.writeValueAsString(newSSRPortConfig);
        log.info("new config is {}", postJson);

        // Save JSON string to file
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(fileOutputStream, newSSRPortConfig);
        fileOutputStream.close();
    }
}

