/**
 * Copyright 2020 bejson.com
 */
package com.securingweb.vpn.entity.dto.V2RayConfig;

import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2020-02-23 0:41:28
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Rules {

    private String type;
    private List<String> ip;
    private String outboundTag;

}