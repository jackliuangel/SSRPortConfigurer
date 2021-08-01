package com.securingweb.vpn.controller.resolver;

import com.securingweb.vpn.domain.internal.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo{

    String name;

    String authority;

    String oAuth2userName;

    public static UserInfo ofUserProfile(UserProfile userProfile) {
        return UserInfo.builder()
                       .name(userProfile.getName())
                       .authority(userProfile.getAuthority())
                       .oAuth2userName(userProfile.getOAuth2userName())
                       .build();
    }

    public static UserInfo ofName(String name) {
        return UserInfo.builder()
                       .name(name)
                       .build();
    }

}
