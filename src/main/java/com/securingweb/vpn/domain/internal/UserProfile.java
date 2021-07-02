package com.securingweb.vpn.domain.internal;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;

import javax.persistence.*;

@Profile({"Database", "OAuth2Github"})
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="user_profile")
public class UserProfile {
    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String authority;

    @Column(nullable = false)
    String password;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Type(type = "pg-uuid")
    private Long uuid;

}
