package com.securingweb.vpn.config.security;

import com.securingweb.vpn.config.security.handler.CustomAccessDeniedHandler;
import com.securingweb.vpn.config.security.handler.CustomAuthenticationEntryPoint;
import com.securingweb.vpn.config.security.jwt.JwtRequestFilter;
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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Profile("JWT")
@Configuration
@EnableWebSecurity
@Slf4j
public class JWTWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("customAuthenticationFailureHandler")
    AuthenticationFailureHandler customAuthenticationFailureHandler;

    String[] staticResources = {
            "/images/**",
            "/favicon.ico"
    };

    @Autowired
    @Qualifier("jwtRequestFilter")
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.exceptionHandling()
            .accessDeniedHandler(new CustomAccessDeniedHandler())
            .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
            .and().sessionManagement()
            //不使用session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.csrf().disable()
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)  //验证请求是否正确
            .authorizeRequests()
            .antMatchers(staticResources).permitAll()
            .antMatchers("/index").permitAll()
            .antMatchers("/jwtAuthenticate").permitAll()
            .antMatchers("/SSR/set/**").hasAuthority("admin")
            .antMatchers("/V2Ray/set/**").hasAuthority("admin")
            .antMatchers("/actuator/**").hasAuthority("viewer")
            .anyRequest().authenticated()
            .and()
            .formLogin()
//           no need this line because we let jwtAuthenticate to redirect to /loginSuccess with jwt token info.
//            .defaultSuccessUrl("/loginSuccess")
            .failureHandler(customAuthenticationFailureHandler)
            .loginPage("/jwt_login")
            .permitAll()
            .and()
            .logout()
            .permitAll()
        ;
    }

    /**
     * for returning jwt with user info, such name, but no password
     */
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
