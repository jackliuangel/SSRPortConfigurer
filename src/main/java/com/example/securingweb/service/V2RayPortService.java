package com.example.securingweb.service;


import com.example.securingweb.entity.V2RayServerRootConfig;
import com.example.securingweb.entity.VPNConfig;
import com.example.securingweb.utility.JsonFileUtil;
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
        V2RayServerRootConfig v2RayServerRootConfig = JsonFileUtil.readFromFile(path, com.example.securingweb.entity.V2RayServerRootConfig.class);
        return v2RayServerRootConfig.getInbounds().get(0).getPort();

    }

    public void configPort(Integer newPort) {
        try {
            V2RayServerRootConfig v2RayServerRootConfig = JsonFileUtil.readFromFile(path, com.example.securingweb.entity.V2RayServerRootConfig.class);
            v2RayServerRootConfig.getInbounds().get(0).setPort(newPort);
            JsonFileUtil.writeFile(v2RayServerRootConfig, path);
        } catch (Exception e) {
            log.error("write port config fails with exception {}", e.toString());
        }


    }

//
//    private V2RayServerRootConfig readFromFile() throws Exception {
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        // Read JSON file and convert to java object
//        InputStream fileInputStream = new FileInputStream(path);
//        V2RayServerRootConfig v2RayServerRootConfig = mapper.readValue(fileInputStream, V2RayServerRootConfig.class);
//        fileInputStream.close();
//        return v2RayServerRootConfig;
//
//    }
//
//
//    private void writeFile(V2RayServerRootConfig v2RayServerRootConfig) throws Exception {
//
//        ObjectMapper mapper = new ObjectMapper();
//        String postJson = mapper.writeValueAsString(v2RayServerRootConfig);
//        log.info("new config is {}", postJson);
//
//        // Save JSON string to file
//        FileOutputStream fileOutputStream = new FileOutputStream(path);
//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
//        mapper.writeValue(fileOutputStream, v2RayServerRootConfig);
//        fileOutputStream.close();
//    }
}

