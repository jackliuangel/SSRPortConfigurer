/**
  * Copyright 2020 bejson.com 
  */
package com.example.securingweb.entity.V2RayConfig;
import java.util.List;

/**
 * Auto-generated: 2020-02-23 0:41:28
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Rules {

    private String type;
    private List<String> ip;
    private String outboundTag;
    public void setType(String type) {
         this.type = type;
     }
     public String getType() {
         return type;
     }

    public void setIp(List<String> ip) {
         this.ip = ip;
     }
     public List<String> getIp() {
         return ip;
     }

    public void setOutboundTag(String outboundTag) {
         this.outboundTag = outboundTag;
     }
     public String getOutboundTag() {
         return outboundTag;
     }

}