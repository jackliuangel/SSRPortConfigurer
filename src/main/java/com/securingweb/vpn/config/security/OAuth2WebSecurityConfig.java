package com.securingweb.vpn.config.security;

import com.securingweb.vpn.config.security.oauth2.CustomizedInMemoryOAuth2AuthorizedClientService;
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
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * refer to https://www.baeldung.com/spring-security-5-oauth2-login
 */

@Profile("OAuth2Github")
@PropertySource("classpath:application-OAuth2Github.properties")
@Configuration
@EnableWebSecurity
public class OAuth2WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //bean life cycle issue to need to set env by EnvironmentAware

    private static List<String> clients = Arrays.asList("github");

    private static String CLIENT_PROPERTY_KEY
            = "spring.security.oauth2.client.registration.";
    /**
     * No need to save @Authentication in SecurityContext because it is done by Spring Security
     * refer to
     *
     * @class SecurityContextPersistenceFilter
     */

    @Autowired
    @Qualifier("customAuthenticationFailureHandler")
    AuthenticationFailureHandler customAuthenticationFailureHandler;
    private String[] staticResources = {
            "/images/**",
    };

    private ClientRegistration getRegistration(String client) {
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
            .antMatchers("/SSR/set/**").hasAuthority("admin")
            .antMatchers("/V2Ray/set/**").hasAuthority("admin")
            .antMatchers("/actuator/**").hasAuthority("viewer")
            .anyRequest()
            .authenticated()
            .and()
            .oauth2Login()
            .clientRegistrationRepository(clientRegistrationRepository())
            .authorizedClientService(authorizedClientService())
            .loginPage("/oauth2_login")
            .defaultSuccessUrl("/loginSuccess")
            .failureHandler(customAuthenticationFailureHandler)
            .and()
            .logout()
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
                                                        .map(c -> getRegistration(c))
                                                        .filter(registration -> registration != null)
                                                        .collect(Collectors.toList());

        return new InMemoryClientRegistrationRepository(registrations);
    }

    //
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
