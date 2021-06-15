package com.securingweb.vpn;

import com.securingweb.vpn.domain.internal.UserProfileRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
//@ComponentScan("com.example.securingweb.config.db")
 class SecuringWebApplicationTests {
    @Value("${V2Ray.Command}")
    String command;
    @Autowired
    UserProfileRepository userProfileRepository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void loginWithValidUserThenAuthenticated() throws Exception {
        log.info("should read from application-test.prop {}", command);

        FormLoginRequestBuilder login = formLogin()
                .user("jack")
                .password("20h");

        mockMvc.perform(login)
               .andExpect(authenticated().withUsername("jack"));
    }

    @Test
     void loginWithInvalidUserThenUnauthenticated() throws Exception {
        FormLoginRequestBuilder login = formLogin()
                .user("invalid")
                .password("invalidpassword");

        mockMvc.perform(login)
               .andExpect(unauthenticated());
    }

    @Test
     void accessUnsecuredResourceThenOk() throws Exception {
        mockMvc.perform(get("/"))
               .andExpect(status().isOk());
    }

    @Test
     void accessSecuredResourceUnauthenticatedThenRedirectsToLogin() throws Exception {
        mockMvc.perform(get("/hello"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser
     void accessSecuredResourceAuthenticatedThenOk() throws Exception {
        mockMvc.perform(get("/hello"))
               .andExpect(status().isOk());
    }


    @Test
     void testDB() {
        var list = userProfileRepository.findAll();
        Assertions.assertThat(list).hasSize(1);

    }


}
