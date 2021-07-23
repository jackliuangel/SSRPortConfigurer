package com.securingweb.vpn.audit;

public enum UserAuditAction {
    LOGIN_SUCCESSFUL,
    LOGIN_FAIL,
//    LOGOUT, //ignored because the token will expired in some time so there is no explicit logout
    READ_PORT,
    UPDATE_PORT_SUCCESSFUL,
    UPDATE_PORT_FAIL,
}
