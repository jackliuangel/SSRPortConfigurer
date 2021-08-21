package com.securingweb.vpn.controller;


import com.securingweb.vpn.audit.UserAuditAction;
import com.securingweb.vpn.audit.annotation.Audit;
import com.securingweb.vpn.audit.annotation.AuditActor;
import com.securingweb.vpn.audit.annotation.AuditField;
import com.securingweb.vpn.entity.property.V2RayProperties;
import com.securingweb.vpn.controller.resolver.UserInfo;
import com.securingweb.vpn.metrics.MetricsTracker;
import com.securingweb.vpn.service.V2RayPortService;
import com.securingweb.vpn.utility.CommandUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/V2Ray")
@Slf4j
public class V2RayPortController{

    @Autowired
    V2RayPortService v2RayPortService;

    @Autowired
    V2RayProperties v2RayProperties;

    @Autowired
    MetricsTracker metricsTracker;

    @CachePut("v2Ray")
    @GetMapping("/set/{portNumber}")
    @ResponseStatus(HttpStatus.CREATED)
    @Audit(UserAuditAction.UPDATE_PORT_SUCCESSFUL)
    public String updateV2RayPort(@AuditField @PathVariable("portNumber") Integer portNumber, @AuditActor UserInfo currentUserInfo) {
        try {
            log.debug("V2Ray updateV2RayPort");
            v2RayPortService.configPort(portNumber);
            String result = CommandUtil.run(v2RayProperties.getCommand());


            return v2RayProperties.getCommand() + "\n\n" + result;
        } catch (Exception e) {

            return "update port failed";
        }
    }

    @Cacheable("v2Ray")
    @GetMapping("/")
    @Audit(UserAuditAction.READ_PORT)
    public Integer getV2RayPort(@AuditActor  UserInfo currentUserInfo) throws Exception {

        log.debug("V2Ray getSSRPort invoked by {}", currentUserInfo);
        metricsTracker.recordQueryCounts(1, currentUserInfo.getName(), "V2Ray");
        return v2RayPortService.readPort();
    }

}
