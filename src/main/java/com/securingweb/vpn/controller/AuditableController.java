package com.securingweb.vpn.controller;

import com.securingweb.vpn.audit.UserAuditAction;
import com.securingweb.vpn.audit.annotation.Audit;
import com.securingweb.vpn.audit.annotation.AuditField;

class AuditableController {

    @Audit(UserAuditAction.UPDATE_PORT_FAIL)
    void updatePortFailAction(@AuditField String userName) {
    }

    @Audit(UserAuditAction.UPDATE_PORT_SUCCESSFUL)
    void updatePortAction(@AuditField String userName) {
    }

    @Audit(UserAuditAction.READ_PORT)
    void readPortAction(@AuditField String userName) {
    }
}
