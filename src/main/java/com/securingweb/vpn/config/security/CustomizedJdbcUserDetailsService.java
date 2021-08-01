package com.securingweb.vpn.config.security;

import com.securingweb.vpn.audit.UserAuditAction;
import com.securingweb.vpn.audit.UserAuditEvent;
import com.securingweb.vpn.controller.resolver.UserInfo;
import com.securingweb.vpn.domain.internal.UserProfile;
import com.securingweb.vpn.domain.internal.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * This class implement the UserDetailsService
 * because there are 2 db in application, which should not autoconfig
 */
public class CustomizedJdbcUserDetailsService implements UserDetailsService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserProfile userProfile = userProfileRepository.findByName(name);

        if (userProfile != null) {
            loginSuccessAction(UserInfo.ofUserProfile(userProfile));
        } else {
            loginFailAction(UserInfo.ofName(name));
        }

        return User
                .withUsername(userProfile.getName())
                .password(userProfile.getPassword())
                .authorities(userProfile.getAuthority())
                .build();
    }

    private void loginFailAction(UserInfo userInfo) {
        applicationEventPublisher.publishEvent(new UserAuditEvent(userInfo, UserAuditAction.LOGIN_FAIL, null));
    }

    private void loginSuccessAction(UserInfo userInfo) {
        applicationEventPublisher.publishEvent(new UserAuditEvent(userInfo, UserAuditAction.LOGIN_SUCCESSFUL, null));
    }
}
