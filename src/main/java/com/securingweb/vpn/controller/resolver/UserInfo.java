package com.securingweb.vpn.controller.resolver;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    String name;

    String authority;

    String uuid;

    String clientId;

    String oAuth2userName;
}
