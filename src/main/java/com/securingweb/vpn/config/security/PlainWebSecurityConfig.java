package com.securingweb.vpn.config.security;

import com.securingweb.vpn.config.security.handler.CustomAccessDeniedHandler;
import com.securingweb.vpn.config.security.handler.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Profile("NoDB")
@Configuration
@EnableWebSecurity
public class PlainWebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * No need to save @Authentication in SecurityContext because it is done by Spring Security
     * refer to
     * @SecurityContextPersistenceFilter
     */


    @Autowired
    @Qualifier("customAuthenticationFailureHandler")
    AuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
            .accessDeniedHandler(new CustomAccessDeniedHandler())
            .authenticationEntryPoint(new CustomAuthenticationEntryPoint());

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/index").permitAll()
                .antMatchers("/SSR/set/**").hasAuthority("admin")
                .antMatchers("/V2Ray/set/**").hasAuthority("admin")
                .antMatchers("/actuator/**").hasAuthority("viewer")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/home")
                .failureHandler(customAuthenticationFailureHandler)
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails Jack =
                User
                        .withUsername("jack")
                        .password(passwordEncoder().encode("603"))
                        .authorities("admin")
                        .build();

        UserDetails Jason =
                User
                        .withUsername("jason")
                        .password(passwordEncoder().encode("603"))
                        .authorities("admin")
                        .build();

        UserDetails Angel =
                User
                        .withUsername("angel")
                        .password(passwordEncoder().encode("603"))
                        .authorities("viewer")
                        .build();

        return new InMemoryUserDetailsManager(Jack, Jason, Angel);
    }

    /**
     * https://mkyong.com/spring-boot/spring-security-there-is-no-passwordencoder-mapped-for-the-id-null/
     * Prior to Spring Security 5.0 the default PasswordEncoder was NoOpPasswordEncoder which required plain text passwords.
     * In Spring Security 5, the default is DelegatingPasswordEncoder, which required Password Storage Format.
     * So this bean is needed.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
