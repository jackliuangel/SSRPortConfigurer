package com.securingweb.vpn.utility;

import com.securingweb.vpn.entity.VPNConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

@Slf4j
@UtilityClass
public class JsonFileUtil {
    @Autowired
    @Qualifier("jacksonObjectMapper")
    ObjectMapper objectMapper;

    public void writeFile(VPNConfig vpnConfig, String path) throws Exception {

        String postJson = objectMapper.writeValueAsString(vpnConfig);
        log.info("VPN config is {}", postJson);

        // Save JSON string to file
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(fileOutputStream, vpnConfig);
        fileOutputStream.close();
    }

    public <T extends VPNConfig> T readFromFile(String path, Class<T> clazz) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        // Read JSON file and convert to java object
        InputStream fileInputStream = new FileInputStream(path);
        T vpnConfig = mapper.readValue(fileInputStream, clazz);
        fileInputStream.close();
        return vpnConfig;

    }

}
