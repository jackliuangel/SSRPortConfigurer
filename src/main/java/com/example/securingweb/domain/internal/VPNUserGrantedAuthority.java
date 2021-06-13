package com.example.securingweb.domain.internal;

import lombok.Value;
import org.springframework.security.core.GrantedAuthority;

@Value
public class VPNUserGrantedAuthority implements GrantedAuthority {

    private String authority;

    VPNUserGrantedAuthority(UserProfile userProfile) {
        authority = userProfile.getAuthority();
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
