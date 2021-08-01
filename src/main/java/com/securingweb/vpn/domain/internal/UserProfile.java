package com.securingweb.vpn.domain.internal;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

//@Profile({"JWT", "OAuth2Github"})
@Profile("!JSession")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "USER_PROFILE")
public class UserProfile {
    @NotNull
    @Column
    String name;

    @Column
    String authority;

    @Column
    String password;

    @Column
    String clientId;

    @Column
    String oAuth2userName;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long uuid;

}
