package com.securingweb.vpn.config.security;

import com.securingweb.vpn.audit.UserAuditAction;
import com.securingweb.vpn.audit.UserAuditEvent;
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
    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserProfile userProfile = userProfileRepository.findByName(name);

        if (userProfile != null) {
            applicationEventPublisher.publishEvent(new UserAuditEvent(name, UserAuditAction.LOGIN_SUCCESSFUL));
        } else {
            applicationEventPublisher.publishEvent(new UserAuditEvent(name, UserAuditAction.LOGIN_FAIL));
        }

        return User
                .withUsername(userProfile.getName())
                .password(userProfile.getPassword())
                .authorities(userProfile.getAuthority())
                .build();
    }
}
