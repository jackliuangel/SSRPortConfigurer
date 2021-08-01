package com.securingweb.vpn.domain.common;


import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Profile({"JWT", "OAuth2Github"})
@Profile("!JSession")
@Repository
public interface UserAuditRepository extends JpaRepository<UserAudit, Long> {
    //    UserAudit findByUserName(String userName);
    List<UserAudit> findAllByUserName(String userName);
}
