package com.securingweb.vpn.config.security.oauth2;


import com.securingweb.vpn.domain.internal.UserProfile;
import com.securingweb.vpn.domain.internal.UserProfileRepository;
import com.securingweb.vpn.exception.UserLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 即使通过Oauth2 认证的用户， 如果不存在与UserProfile的DB中，也不让通过！
 */
@Profile("OAuth2Github")
@Component("CustomOAuth2AuthClientFilter")
public class CustomOAuth2AuthClientFilter extends OncePerRequestFilter {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();

        if (currentAuthentication == null) {
            chain.doFilter(request, response);
            return;
        }

        UserProfile userProfile = userProfileRepository.findByOAuth2userName(currentAuthentication.getName());
        if (userProfile != null) {
            chain.doFilter(request, response);
        } else {
            throw new UserLoginException("username does not exist in user profile repository");
        }
    }
}
