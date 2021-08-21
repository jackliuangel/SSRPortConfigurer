package com.securingweb.vpn.config.security;

import com.securingweb.vpn.config.security.handler.CustomAccessDeniedHandler;
import com.securingweb.vpn.config.security.handler.CustomAuthenticationEntryPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Slf4j
@Profile("JSessionH2")
@Configuration
@EnableWebSecurity
public class JSessionH2WebSecurityConfig extends WebSecurityConfigurerAdapter {

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
            "/favicon.ico",
            "/h2-console/**",
            "/CORSDemo/**",
            "/actuator/*",
    };


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
            .accessDeniedHandler(new CustomAccessDeniedHandler())
            .authenticationEntryPoint(new CustomAuthenticationEntryPoint());

        http
                .cors()
                .and() //enable CORS to demo CORS
                .authorizeRequests()
                .antMatchers(staticResources).permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/cors").permitAll()
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
        return new CustomizedJdbcUserDetailsService();
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
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
