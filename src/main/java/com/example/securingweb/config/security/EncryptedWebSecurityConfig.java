package com.example.securingweb.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Profile("local")
//@ConditionalOnMissingBean(name = "com.example.securingweb.config.security.PlainWebSecurityConfig")
//@Configuration
//@EnableWebSecurity
public class EncryptedWebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
//                .addFilterAt(authenticationWebFilter, AUTHENTICATION)
                .authorizeRequests()
                .antMatchers("/SSR/set/**").hasAuthority("admin")
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

    //TODO: add AuthencationWebFilter

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new ApplicationJdbcUserDetailsManager();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }


}
