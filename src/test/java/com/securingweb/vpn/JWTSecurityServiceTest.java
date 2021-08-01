package com.securingweb.vpn;

import com.securingweb.vpn.domain.common.UserAuditRepository;
import com.securingweb.vpn.domain.internal.UserProfileRepository;
import com.securingweb.vpn.service.SSRPortService;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = {"JWT"})
class JWTSecurityServiceTest {
    @Value("${V2Ray.Command}")
    String command;

    @MockBean
    SSRPortService mockPortService;

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    UserAuditRepository userAuditRepository;

    private MockMvc mockMvc;

    //这个ApplicationContext是@SpringBootTest的Application context，
    // 所以里面有DB bean，所以EncryptedWebSecurityConfig 里用到的DB能初始化
    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    void setup() throws Exception {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .build();

        when(mockPortService.readPort()).thenReturn(1234);
    }

    @Test
    void loginWithValidUserThenAuthenticated() throws Exception {
        mockMvc.perform(post("/jwtAuthenticate")
                .param("username", "jack")
                .param("password", "1234"))
               .andExpect(model().attributeExists("name"));
    }

    @Test
    void loginWithInvalidUserThenUnauthenticated() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder login = formLogin("/jwt_login")
                .user("testJack")
                .password("12345");

        mockMvc.perform(login)
               .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser(username = "testJack", password = "1234")
    void getValidSSRConfigWithAuthenticatedUser() throws Exception {
        log.info("should read from application-test.prop {}", command);

        mockMvc.perform(get("/SSR/"))
               .andDo(print())
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testJack", password = "1234")
    void setValidSSRConfigWithAuthenticatedUser() throws Exception {

        mockMvc.perform(get("/SSR/set/5678"))
               .andDo(print())
               .andExpect(status().isCreated());

    }

    @Test
    @WithMockUser(username = "testJack", password = "1234")
    void getValidV2RayConfigWithAuthenticatedUser() throws Exception {
        log.info("should read from application-test.prop {}", command);

        mockMvc.perform(get("/V2Ray/"))
               .andDo(print())
               .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "testJack", password = "1234")
    void setValidV2RayConfigWithAuthenticatedUser() throws Exception {

        mockMvc.perform(get("/V2Ray/set/5678"))
               .andDo(print())
               .andExpect(status().isCreated());
    }

    @Test
    void testDB() {

        var userProfiles = userProfileRepository.findAll();
        assertThat(userProfiles.size()).isGreaterThan(1);

        var userAudits = userAuditRepository.findAll();
        assertThat(userAudits.size()).isGreaterThan(1);
    }
}
