package com.securingweb.vpn.domain.common;


import com.securingweb.vpn.audit.UserAuditAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

//@Profile({"JWT", "OAuth2Github"})
@Profile("!JSession")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_audit")
@EntityListeners(AuditingEntityListener.class)
public class UserAudit {
    @Column(nullable = false)
    String userName;

    //    @CreationTimestamp vs @CreatedDate
    //    不用hibernate的audit，用spring data
    @CreatedDate
    @Column(columnDefinition = "timestamp with timezone")
    private LocalDateTime lastUpdated; //it is created and then should not be updated again

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserAuditAction action;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long uuid;

}
