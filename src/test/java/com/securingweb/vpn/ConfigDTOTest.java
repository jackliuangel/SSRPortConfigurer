package com.securingweb.vpn;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.securingweb.vpn.entity.SSRPortConfig;
import com.securingweb.vpn.entity.V2RayConfig.V2RayServerRootConfig;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigDTOTest {

    @Test
    void testDeserializeV2RayConfig() throws Exception {

        Resource classPathResource = new ClassPathResource("V2Ray-config.json");

        ObjectMapper mapper = new ObjectMapper();

        V2RayServerRootConfig config = mapper.readValue(classPathResource.getFile(), V2RayServerRootConfig.class);

        assertThat(config).isNotNull()
                          .hasFieldOrProperty("inbounds")
                          .hasFieldOrProperty("outbounds");
    }

    @Test
    void testDeserializeSSRConfig() throws Exception {

        Resource classPathResource = new ClassPathResource("SSR-config.json");

        ObjectMapper mapper = new ObjectMapper();

        SSRPortConfig config = mapper.readValue(classPathResource.getFile(), SSRPortConfig.class);

        assertThat(config).isNotNull()
                          .hasFieldOrPropertyWithValue("server_port", 1234);
    }

    @Test
    void testTestJsonDTO() throws Exception {

        Resource classPathResource = new ClassPathResource("TestJsonDTO.json");
        ObjectMapper mapper = new ObjectMapper();
        TestJsonDTO config = mapper.readValue(classPathResource.getFile(), TestJsonDTO.class);
        assertThat(config).isNotNull()
                          .hasFieldOrPropertyWithValue("isGraduated", true);


        TestJsonDTO testJsonDTO = new TestJsonDTO(true);
        String json = mapper.writeValueAsString(testJsonDTO);
        assertThat(json).isNotBlank();

    }


}
