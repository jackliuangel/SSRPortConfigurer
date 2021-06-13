package com.example.securingweb.config;

import com.example.securingweb.domain.UserProfile;
import com.example.securingweb.domain.UserProfileRepository;
import com.example.securingweb.domain.VpnUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * This class implement the UserDetailsService because there are 2 db in application, which should not autoconfig
 */
public class ApplicationJdbcUserDetailsManager implements UserDetailsService {

    @Autowired
    private UserProfileRepository userProfileRepository;


    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserProfile userProfile = userProfileRepository.findByUserName(name);// name is used as repository ID
        return new VpnUserDetails(userProfile);
    }
}
