package com.securingweb.vpn;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponse;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = {"OAuth2Github"})
@Disabled
class OAuth2SecurityServiceTest {

    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "testJack", password = "1234")
    void getValidSSRConfigWithAuthenticatedUser() throws Exception {

        OAuth2User Jack =
                new DefaultOAuth2User(AuthorityUtils.createAuthorityList("Admin"), Collections.singletonMap("jackliluangel","2834972984723"), "jackliluangel");

        OAuth2LoginAuthenticationToken oAuth2LoginAuthenticationToken =
                new OAuth2LoginAuthenticationToken(
                        ClientRegistration.withRegistrationId("github").authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE).build(),
                        new OAuth2AuthorizationExchange(OAuth2AuthorizationRequest.authorizationCode().build(), OAuth2AuthorizationResponse.success("d").build()),
                        Jack,
                        AuthorityUtils.createAuthorityList("Admin"), new OAuth2AccessToken(null, null, null, null));

        SecurityContextHolder.getContext().setAuthentication(oAuth2LoginAuthenticationToken);


        mockMvc.perform(get("/SSR/"))
               .andDo(print())
               .andExpect(status().isOk());
    }


}
