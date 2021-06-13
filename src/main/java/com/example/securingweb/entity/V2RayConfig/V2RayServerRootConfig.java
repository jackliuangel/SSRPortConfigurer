/**
 * Copyright 2020 bejson.com
 */
package com.example.securingweb.entity;

import com.example.securingweb.entity.V2RayConfig.Inbounds;
import com.example.securingweb.entity.V2RayConfig.Outbounds;
import com.example.securingweb.entity.V2RayConfig.Routing;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2020-02-23 0:41:28
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
//@EqualsAndHashCode(callSuper = true)
@Data
public class V2RayServerRootConfig implements VPNConfig {

    private List<Inbounds> inbounds;
    private List<Outbounds> outbounds;
    private Routing routing;

}