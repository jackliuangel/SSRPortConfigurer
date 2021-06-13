package com.example.securingweb.domain.internal;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

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
