package com.securingweb.vpn;

import com.securingweb.vpn.entity.dto.FeedbackDTO;
import com.sun.tools.javac.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * refer to @link https://medium.com/@ashwin.jammihal/upload-both-request-body-and-multipart-file-using-springs-resttemplate-8207ab6069b6
 */
@ActiveProfiles(profiles = {"JSession"})
@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GreetingControllerTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    @WithMockUser(username = "jack", password = "1234", authorities = "admin")
    public void testUploadWithDTO_Attachment() throws IOException {

        MultiValueMap<String, Object> multipartRequest = new LinkedMultiValueMap<>();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);//Main request's headers

        HttpHeaders requestHeadersAttachment = new HttpHeaders();
        requestHeadersAttachment.setContentType(MediaType.MULTIPART_FORM_DATA);// extract mediatype from file extension

        Resource classPathResource = new ClassPathResource("abstract.JPG");
        File file = classPathResource.getFile();

        byte[] bytes = Files.readAllBytes(file.toPath());

        ByteArrayResource fileAsResource = new ByteArrayResource(bytes) {
            @Override
            public String getFilename() {
                return file.getAbsoluteFile().getName()+"1";
            }
        };

        List<ByteArrayResource> fileAsResourceList = List.of(fileAsResource, fileAsResource);

        multipartRequest.addAll("attachments", fileAsResourceList);

        HttpHeaders requestHeadersJSON = new HttpHeaders();
        requestHeadersJSON.setContentType(MediaType.APPLICATION_JSON);

        FeedbackDTO feedbackDTO = FeedbackDTO.builder().note("nice experience").title("almost perfect").build();

        HttpEntity<FeedbackDTO> requestEntityJSON = new HttpEntity<>(feedbackDTO, requestHeadersJSON);

        multipartRequest.set("feedbackDTO", requestEntityJSON);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(multipartRequest, requestHeaders);//final request

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/feedback", HttpMethod.POST, requestEntity, String.class);

        assert (response).getStatusCode().is2xxSuccessful();
    }
}