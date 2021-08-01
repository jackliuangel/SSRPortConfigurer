package com.securingweb.vpn.controller;

import com.securingweb.vpn.audit.UserAuditAction;
import com.securingweb.vpn.audit.annotation.Audit;
import com.securingweb.vpn.audit.annotation.AuditActor;
import com.securingweb.vpn.controller.resolver.UserInfo;
import com.securingweb.vpn.domain.common.UserAudit;
import com.securingweb.vpn.domain.common.UserAuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/UserAudit")
class UserAuditController {
    @Autowired
    UserAuditRepository userAuditRepository;

    @GetMapping
    @Audit(UserAuditAction.VIEW_AUDIT_HISTORY)
    List<UserAudit> viewAuditHistory(@AuditActor UserInfo currentUserInfo) {
        return userAuditRepository.findAllByUserName(currentUserInfo.getName());
    }
}
