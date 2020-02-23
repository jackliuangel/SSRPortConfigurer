/**
  * Copyright 2020 bejson.com 
  */
package com.example.securingweb.entity.V2RayConfig;

import com.example.securingweb.entity.Settings;

/**
 * Auto-generated: 2020-02-23 0:41:28
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Inbounds {

    private int port;
    private String protocol;
    private Settings settings;
    public void setPort(int port) {
         this.port = port;
     }
     public int getPort() {
         return port;
     }

    public void setProtocol(String protocol) {
         this.protocol = protocol;
     }
     public String getProtocol() {
         return protocol;
     }

    public void setSettings(Settings settings) {
         this.settings = settings;
     }
     public Settings getSettings() {
         return settings;
     }

}