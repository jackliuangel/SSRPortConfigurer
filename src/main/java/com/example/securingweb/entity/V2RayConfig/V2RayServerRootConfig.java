/**
  * Copyright 2020 bejson.com 
  */
package com.example.securingweb.entity;
import com.example.securingweb.entity.V2RayConfig.Inbounds;
import com.example.securingweb.entity.V2RayConfig.Outbounds;
import com.example.securingweb.entity.V2RayConfig.Routing;

import java.util.List;

/**
 * Auto-generated: 2020-02-23 0:41:28
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class V2RayServerRootConfig {

    private List<Inbounds> inbounds;
    private List<Outbounds> outbounds;
    private Routing routing;
    public void setInbounds(List<Inbounds> inbounds) {
         this.inbounds = inbounds;
     }
     public List<Inbounds> getInbounds() {
         return inbounds;
     }

    public void setOutbounds(List<Outbounds> outbounds) {
         this.outbounds = outbounds;
     }
     public List<Outbounds> getOutbounds() {
         return outbounds;
     }

    public void setRouting(Routing routing) {
         this.routing = routing;
     }
     public Routing getRouting() {
         return routing;
     }

}