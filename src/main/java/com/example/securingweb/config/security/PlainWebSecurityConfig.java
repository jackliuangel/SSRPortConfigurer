package com.example.securingweb.config.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

//@Profile("prod")
@Primary
@Configuration
@EnableWebSecurity
public class PlainWebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers( "/SSR/set/**").hasAuthority("admin")
                .antMatchers("/V2Ray/set/**").hasAuthority("admin")
                .antMatchers("/actuator/**").hasAuthority("viewer")
                .anyRequest().authenticated()
                .and()
                .formLogin()
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
                User.withDefaultPasswordEncoder()
                        .username("jack")
                        .password("603")
                        .authorities("admin")
                        .build();

        UserDetails Jason =
                User.withDefaultPasswordEncoder()
                        .username("jason")
                        .password("603")
                        .authorities("admin")
                        .build();

        UserDetails Kelvin =
                User.withDefaultPasswordEncoder()
                        .username("angel")
                        .password("1006")
                        .authorities("viewer")
                        .build();

        return new InMemoryUserDetailsManager(Jack, Jason, Kelvin);
    }
}
