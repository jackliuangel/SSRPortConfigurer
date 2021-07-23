package com.securingweb.vpn.config.security;

import com.securingweb.vpn.config.security.handler.CustomAccessDeniedHandler;
import com.securingweb.vpn.config.security.handler.CustomAuthenticationEntryPoint;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Profile("JSession")
@Configuration
@EnableWebSecurity
public class JSessionWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("customAuthenticationFailureHandler")
    AuthenticationFailureHandler customAuthenticationFailureHandler;

    /**
     * for static image resource accessing, refer to
     *
     * @link https://stackoverflow.com/questions/44455900/spring-security-login-page-images
     */
    private String[] staticResources = {
            "/images/**",
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.exceptionHandling()
            .accessDeniedHandler(new CustomAccessDeniedHandler())
            .authenticationEntryPoint(new CustomAuthenticationEntryPoint());

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(staticResources).permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/SSR/set/**").hasAuthority("admin")
                .antMatchers("/V2Ray/set/**").hasAuthority("admin")
                .antMatchers("/actuator/**").hasAuthority("viewer")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/loginSuccess")
                .failureHandler(customAuthenticationFailureHandler)
                .loginPage("/jsession_login")
                .permitAll()
                .and()
                .logout()//will redirect to /loginPage with param
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
     * @link https://mkyong.com/spring-boot/spring-security-there-is-no-passwordencoder-mapped-for-the-id-null/
     * <p>
     * Prior to Spring Security 5.0 the default PasswordEncoder was NoOpPasswordEncoder which required plain text passwords.
     * In Spring Security 5, the default is DelegatingPasswordEncoder, which required Password Storage Format.
     * So this bean is needed.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
