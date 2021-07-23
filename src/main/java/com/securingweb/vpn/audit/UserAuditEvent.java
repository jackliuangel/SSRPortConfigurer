package com.securingweb.vpn.audit;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public final class UserAuditEvent {

    private final String userName;
    private final UserAuditAction userAuditAction;

}
