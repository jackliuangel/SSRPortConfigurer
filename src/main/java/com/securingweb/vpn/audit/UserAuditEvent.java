package com.securingweb.vpn.audit;

import com.securingweb.vpn.controller.resolver.UserInfo;
import lombok.Getter;
import lombok.Value;

import java.util.Map;

@Value
@Getter
public class UserAuditEvent {

    String userName;

    UserAuditAction userAuditAction;

    String comments;

    public UserAuditEvent(UserInfo userInfo, UserAuditAction userAuditAction, Map<String, String> data) {
        userName = userInfo.getName();
        this.userAuditAction = userAuditAction;
        if (data != null) {
            comments = data.toString();
        } else {
            comments = "";
        }
    }

    public UserAuditEvent(String userName, UserAuditAction userAuditAction, Map<String, String> data) {
        this.userName = userName;
        this.userAuditAction = userAuditAction;
        if (data != null) {
            comments = data.toString();
        } else {
            comments = "";
        }
    }

}
