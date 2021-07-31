package com.securingweb.vpn.audit;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public final class UserAuditEvent {

    private final String userName;

    private final UserAuditAction userAuditAction;

    public UserAuditEvent(Map<String, String> data) {
        userName = data.get("userName");
        userAuditAction = UserAuditAction.valueOf(data.get("userAuditAction"));
    }

}
