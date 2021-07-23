package com.securingweb.vpn.domain.common;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuditRepository extends JpaRepository<UserAudit, Long> {
    UserAudit findByUserName(String userName);
}
