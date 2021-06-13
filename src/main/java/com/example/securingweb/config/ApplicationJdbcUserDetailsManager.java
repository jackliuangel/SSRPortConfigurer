package com.example.securingweb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *  This class implement the UserDetailsService because there are 2 db in application, which should not autoconfig
 */
public class ApplicationJdbcUserDetailsManager implements UserDetailsService {

   @Autowired

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
