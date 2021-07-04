package com.securingweb.vpn.controller.resolver;

import com.securingweb.vpn.domain.internal.UserProfile;
import com.securingweb.vpn.domain.internal.UserProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.stream.Collectors;

/**
 * follow https://reflectoring.io/spring-boot-argumentresolver/
 */
@Slf4j
public class UserInfoResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameter().getType() == UserInfo.class;
    }

    @Override
    public UserInfo resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {

//        String requestPath = ((ServletWebRequest) webRequest)
//                .getRequest()
//                .getPathInfo();

        UserInfo userInfo = UserInfo.builder().build();

        Object principal = SecurityContextHolder.getContext().getAuthentication();

        log.debug("principle = " + principal);

        if (principal instanceof UsernamePasswordAuthenticationToken) {
            // jsessionid and jwt both uses UsernamePasswordAuthenticationToken
            UsernamePasswordAuthenticationToken upat = (UsernamePasswordAuthenticationToken) principal;

            return UserInfo.builder()
                           .name(upat.getName())
                           .authority(upat.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining()))
                           .build();
        }
        if (principal instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) principal;

            String name = (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("login");

            UserProfile userProfile = userProfileRepository.findByOAuth2userName(oAuth2AuthenticationToken.getName());

            return UserInfo.builder()
                           .name(name + " as " + userProfile.getName())
                           //use Port Configurer authority to overwrite Oauth2 authority
                           //.authority(oAuth2AuthenticationToken.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining()))
                           .authority(userProfile.getAuthority())
                           .build();
        }
        return userInfo;
    }
}
