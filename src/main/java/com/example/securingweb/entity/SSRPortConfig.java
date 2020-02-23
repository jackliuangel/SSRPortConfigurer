package com.example.securingweb.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class SSRPortConfig {
    private String server;
    private Integer server_port;
    private String local_address;
    private Integer local_port;
    private String password;
    private Integer timeout;
    private String method;
    private Boolean fast_open;

}

