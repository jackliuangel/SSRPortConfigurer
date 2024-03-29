/**
 * Copyright 2020 bejson.com
 */
package com.securingweb.vpn.entity.dto.V2RayConfig;

import com.securingweb.vpn.entity.dto.VPNConfig;
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