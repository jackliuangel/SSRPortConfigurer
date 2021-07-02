package com.securingweb.vpn.config;

import com.securingweb.vpn.config.security.handler.CustomAuthenticationFailureHandler;
import com.securingweb.vpn.controller.resolver.UserInfoResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        //whitelist from access security
        registry.addViewController("/index").setViewName("index");
        //the follow 3 line must be added because html pages needs a controller to dispatch
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/jwtLogin").setViewName("jwtLogin");
        registry.addViewController("/oauth_login").setViewName("oauth_login");
        registry.addViewController("/loginSuccess").setViewName("loginSuccess");
        //home is the 1st page after successfully login
        registry.addViewController("/home").setViewName("home");
    }

    //for both cases of  with or without db, this controller will handle the authentication exceptions
    @Bean("customAuthenticationFailureHandler")
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    UserInfoResolver userInfoResolver() {
        return new UserInfoResolver();
    }

    @Override
    public void addArgumentResolvers(
            List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userInfoResolver());
    }
}
