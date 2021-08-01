package com.securingweb.vpn;

import com.securingweb.vpn.audit.UserAuditAction;
import com.securingweb.vpn.domain.common.UserAudit;
import com.securingweb.vpn.domain.common.UserAuditRepository;
import com.securingweb.vpn.service.SSRPortService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = {"JSessionH2"})
class JSessionH2SecurityServiceAndUserAuditTest {
    @Value("${V2Ray.Command}")
    String command;

    @MockBean
    SSRPortService mockPortService;

    @Autowired
    UserAuditRepository userAuditRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void cleanUp(){
        userAuditRepository.deleteAll();
    }

    @Test
    void loginWithValidUserThenAuthenticated() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder login = formLogin("/jsession_login")
                .user("jack")
                .password("603");

        mockMvc.perform(login)
               .andExpect(authenticated());
        await().atLeast(1, TimeUnit.SECONDS);
        List<UserAudit> userAudits = userAuditRepository.findAllByUserName("jack");
        userAudits.sort(Comparator.comparing(UserAudit::getLastUpdated));
        assertThat(userAudits.get(0))
                .as("latest user audit record")
                .hasFieldOrPropertyWithValue("action", UserAuditAction.LOGIN_SUCCESSFUL)
                .hasFieldOrPropertyWithValue("comments", "");
    }

    @Test
    void loginWithInvalidUserThenUnauthenticated() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder login = formLogin("/jsession_login")
                .user("testJack")
                .password("1234-ERROR");

        mockMvc.perform(login)
               .andExpect(unauthenticated());
        await().atLeast(1, TimeUnit.SECONDS);
        List<UserAudit> userAudits = userAuditRepository.findAllByUserName("testJack");
        userAudits.sort(Comparator.comparing(UserAudit::getLastUpdated));
        assertThat(userAudits.get(0))
                .as("latest user audit record")
                .hasFieldOrPropertyWithValue("action", UserAuditAction.LOGIN_FAIL)
                .hasFieldOrPropertyWithValue("comments", "");
    }

    @Test
    @WithMockUser(username = "jack", password = "1234")
    void getValidSSRConfigWithAuthenticatedUser() throws Exception {
        log.info("should read from application-test.prop {}", command);

        mockMvc.perform(get("/SSR/"))
               .andDo(print())
               .andExpect(status().isOk());
        await().atLeast(1, TimeUnit.SECONDS);
        List<UserAudit> userAudits = userAuditRepository.findAllByUserName("jack");
        userAudits.sort(Comparator.comparing(UserAudit::getLastUpdated));
        assertThat(userAudits.get(0))
                .as("latest user audit record")
                .hasFieldOrPropertyWithValue("action", UserAuditAction.READ_PORT)
                .hasFieldOrPropertyWithValue("comments", "");
    }

    @Test
    @WithMockUser(username = "jack", password = "1234", authorities = "admin")
    void setValidSSRConfigWithAuthenticatedUser() throws Exception {
        log.info("should set port {}", command);

        mockMvc.perform(get("/SSR/set/4567"))
               .andDo(print())
               .andExpect(status().isCreated());
        await().atLeast(1, TimeUnit.SECONDS);
        List<UserAudit> userAudits = userAuditRepository.findAllByUserName("jack");
        userAudits.sort(Comparator.comparing(UserAudit::getLastUpdated));
        assertThat(userAudits.get(0))
                .as("latest user audit record")
                .hasFieldOrPropertyWithValue("action", UserAuditAction.UPDATE_PORT_SUCCESSFUL)
                .hasFieldOrPropertyWithValue("comments", "{portNumber=4567}");
    }

    @Test
    @WithMockUser(username = "jack", password = "1234")
    void getValidV2RayConfigWithAuthenticatedUser() throws Exception {
        log.info("should read from application-test.prop {}", command);

        mockMvc.perform(get("/V2Ray/"))
               .andDo(print())
               .andExpect(status().isOk());
        await().atLeast(1, TimeUnit.SECONDS);
        List<UserAudit> userAudits = userAuditRepository.findAllByUserName("jack");
        userAudits.sort(Comparator.comparing(UserAudit::getLastUpdated));
        assertThat(userAudits.get(0))
                .as("latest user audit record")
                .hasFieldOrPropertyWithValue("action", UserAuditAction.READ_PORT)
                .hasFieldOrPropertyWithValue("comments", "");
    }

    @Test
    @WithMockUser(username = "jack", password = "1234", authorities = "admin")
    void setValidV2RayConfigWithAuthenticatedUser() throws Exception {
        log.info("should set port {}", command);

        mockMvc.perform(get("/V2Ray/set/4567"))
               .andDo(print())
               .andExpect(status().isCreated());

        await().atLeast(1, TimeUnit.SECONDS);
        List<UserAudit> userAudits = userAuditRepository.findAllByUserName("jack");
        userAudits.sort(Comparator.comparing(UserAudit::getLastUpdated));
        assertThat(userAudits.get(0))
                .as("latest user audit record")
                .hasFieldOrPropertyWithValue("action", UserAuditAction.UPDATE_PORT_SUCCESSFUL)
                .hasFieldOrPropertyWithValue("comments", "{portNumber=4567}");
    }
}
