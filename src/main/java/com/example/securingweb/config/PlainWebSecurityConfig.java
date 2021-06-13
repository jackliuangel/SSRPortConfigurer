package com.example.securingweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class PlainWebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/SSR").permitAll()
                .antMatchers("/", "/V2Ray").permitAll()
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
                        .password("20h")
                        .roles("USER")
                        .build();

        UserDetails Jason =
                User.withDefaultPasswordEncoder()
                        .username("jason")
                        .password("20h")
                        .roles("USER")
                        .build();

        UserDetails Kelvin =
                User.withDefaultPasswordEncoder()
                        .username("kelvin")
                        .password("force")
                        .roles("USER")
                        .build();
//TODO: create 2 db instance
        //TODO: in local, use docker postGreq
        //TODO: in prod, use localRepository

        return new InMemoryUserDetailsManager(Jack, Jason, Kelvin);
    }
}
