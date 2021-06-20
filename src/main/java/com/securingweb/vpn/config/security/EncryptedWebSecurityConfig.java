package com.securingweb.vpn.config.security;

import com.securingweb.vpn.config.security.JWT.JwtRequestFilter;
import com.securingweb.vpn.config.security.handler.CustomAccessDeniedHandler;
import com.securingweb.vpn.config.security.handler.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class EncryptedWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.exceptionHandling()
            .accessDeniedHandler(new CustomAccessDeniedHandler())
            .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
            .and().sessionManagement()
            //不使用session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        ;

        http.csrf().disable()
//                .addFilterAt(authenticationWebFilter, AUTHENTICATION)
            .authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/jwtAuthenticate").permitAll()
            .antMatchers("/SSR/set/**").hasAuthority("admin")
            .antMatchers("/V2Ray/set/**").hasAuthority("admin")
            .antMatchers("/actuator/**").hasAuthority("viewer")
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .failureHandler(customAuthenticationFailureHandler)
//FIXME: if want to use jsessionid to manage the login status , uncomment it
//            .loginPage("/login")
            .loginPage("/jwtLogin")
            .permitAll()
            .and()
            .logout()
            .permitAll()
        ;

        //验证请求是否正确
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }


    /**
     * for returning jwt user
     */
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new ApplicationJdbcUserDetailsService();
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
