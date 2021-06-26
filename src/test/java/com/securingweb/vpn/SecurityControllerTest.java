package com.securingweb.vpn;

import com.securingweb.vpn.service.SSRPortService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class SecurityControllerTest {
    @Value("${V2Ray.Command}")
    String command;

    @MockBean
    SSRPortService mockPortService;

    private MockMvc mockMvc;

    //这个ApplicationContext是@SpringBootTest的Application context，所以里面有DB bean，所以EncryptedWebSecurityConfig 里用到的DB能初始化
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
    @WithMockUser(username = "jack", password = "1234")
    void getValidSSRConfigWithAuthenticatedUser() throws Exception {
        log.info("should read from application-test.prop {}", command);

        mockMvc.perform(get("/SSR/"))
               .andDo(print())
               .andExpect(status().isOk());
    }
//
//    @Test
//    void loginWithValidUserThenAuthenticated() throws Exception {
//        FormLoginRequestBuilder login = formLogin()
//                .user("jack")
//                .password("1234");
//
//        mockMvc.perform(login)
//               .andExpect(authenticated());
//    }
//
//    @Test
//    void loginWithInvalidUserThenUnauthenticated() throws Exception {
//        FormLoginRequestBuilder login = formLogin()
//                .user("invalid")
//                .password("invalidpassword");
//
//        mockMvc.perform(login)
//               .andExpect(unauthenticated());
//    }
//
//    @Test
//    void accessSecuredResourceUnauthenticatedThenRedirectsToLogin() throws Exception {
//        mockMvc.perform(get("/hello"))
//               .andExpect(status().is3xxRedirection())
//               .andExpect(redirectedUrlPattern("**/login"));
//    }
//
//    @Test
//    @WithMockUser
//    void accessSecuredResourceAuthenticatedThenOk() throws Exception {
//        mockMvc.perform(get("/hello"))
//               .andExpect(status().isOk());
//    }
//

}