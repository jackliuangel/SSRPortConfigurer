package com.securingweb.vpn.config.security;

import com.securingweb.vpn.config.security.oauth2.CustomOAuth2AuthClientFilter;
import com.securingweb.vpn.config.security.oauth2.CustomizedInMemoryOAuth2AuthorizedClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * refer to https://www.baeldung.com/spring-security-5-oauth2-login
 */

@Profile("OAuth2Github")
@PropertySource("classpath:application-OAuth2Github.properties")
@Configuration
@EnableWebSecurity
@Slf4j
public class OAuth2WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static List<String> clients = Arrays.asList("github");

    private static String CLIENT_PROPERTY_KEY
            = "spring.security.oauth2.client.registration.";

    @Autowired
    @Qualifier("customAuthenticationFailureHandler")
    AuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    @Qualifier("CustomOAuth2AuthClientFilter")
    private CustomOAuth2AuthClientFilter customOAuth2AuthClientFilter;

    private String[] staticResources = {
            "/images/**",
            "/favicon.ico"
    };

    private ClientRegistration getRegistration(String client) {
       //hard code the info here, should read it from property files
        String clientId = "736c9357b7c393839817", clientSecret = "5661b767b1e0a64ed16e0a129547cd359bae3062";
        return CommonOAuth2Provider.GITHUB.getBuilder(client)
                                          .clientId(clientId).clientSecret(clientSecret).build();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
            .antMatchers(staticResources).permitAll()
            .antMatchers("/index").permitAll()
            .antMatchers("/oauth2_login").permitAll()
            /**
             * the oauth2 login user will have ROLE_USER only
             * @Class OAuth2AuthenticationToken
             */
//            .antMatchers("/actuator/**").hasAuthority("user")
            .antMatchers("/SSR/set/**").denyAll()
            .antMatchers("/V2Ray/set/**").denyAll()
            .anyRequest()
            .authenticated()
            .and()
            .addFilterAfter(customOAuth2AuthClientFilter, OAuth2LoginAuthenticationFilter.class)
            .oauth2Login()
            .clientRegistrationRepository(clientRegistrationRepository())
            .authorizedClientService(authorizedClientService())
            .loginPage("/oauth2_login")
            .defaultSuccessUrl("/loginSuccess")
            .failureHandler(customAuthenticationFailureHandler)
            .and()
            .logout()//will redirect to /loginPage with param
            .permitAll();
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {
        return new CustomizedInMemoryOAuth2AuthorizedClientService(
                clientRegistrationRepository());
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        List<ClientRegistration> registrations = clients.stream()
                                                        .map(this::getRegistration)
                                                        .filter(Objects::nonNull)
                                                        .collect(Collectors.toList());

        return new InMemoryClientRegistrationRepository(registrations);
    }


//    @Autowired
//    private Environment env;

    //    private ClientRegistration getRegistration(String client) {
//        String clientId = env.getProperty(
//                CLIENT_PROPERTY_KEY + client + ".client-id");
//
//        if (clientId == null) {
//            return null;
//        }
//
//        String clientSecret = env.getProperty(
//                CLIENT_PROPERTY_KEY + client + ".client-secret");
//
//        if (client.equals("github")) {
//            return CommonOAuth2Provider.GITHUB.getBuilder(client)
//                                              .clientId(clientId).clientSecret(clientSecret).build();
//        }
//        return null;
//    }

}
