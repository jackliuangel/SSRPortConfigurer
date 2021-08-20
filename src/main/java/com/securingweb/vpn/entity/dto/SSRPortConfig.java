package com.securingweb.vpn.entity.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
//@EqualsAndHashCode(callSuper = true)
public class SSRPortConfig implements VPNConfig {
    private String server;
    private Integer server_port;
    private String local_address;
    private Integer local_port;
    private String password;
    private Integer timeout;
    private String method;
    private Boolean fast_open;

}

