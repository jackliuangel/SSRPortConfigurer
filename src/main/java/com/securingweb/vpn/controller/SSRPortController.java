package com.securingweb.vpn.controller;


import com.securingweb.vpn.audit.UserAuditAction;
import com.securingweb.vpn.audit.annotation.Audit;
import com.securingweb.vpn.audit.annotation.AuditActor;
import com.securingweb.vpn.audit.annotation.AuditField;
import com.securingweb.vpn.entity.property.SSRProperties;
import com.securingweb.vpn.controller.resolver.UserInfo;
import com.securingweb.vpn.service.SSRPortService;
import com.securingweb.vpn.utility.CommandUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/SSR")
@Slf4j
public class SSRPortController {

    @Autowired
    SSRPortService ssrPortService;

    @Autowired
    SSRProperties ssrProperties;

    @CachePut("ssr")
    @GetMapping("/set/{portNumber}")
    @ResponseStatus(HttpStatus.CREATED)
    @Audit(UserAuditAction.UPDATE_PORT_SUCCESSFUL)
    public String updateSSRPort(@AuditField @PathVariable("portNumber") Integer portNumber, @AuditActor UserInfo currentUserInfo) {
        try {
            log.debug("SSR updateV2RayPort");
            ssrPortService.configPort(portNumber);
            String result = CommandUtil.run(ssrProperties.getCommand());

            return ssrProperties.getCommand() + "\n\n" + result;
        } catch (Exception e) {
            return "update port failed ";
        }
    }

    @Cacheable("ssr")
    @GetMapping("/")
    @Audit(UserAuditAction.READ_PORT)
    public Integer getSSRPort(@AuditActor UserInfo currentUserInfo) throws Exception {

        log.debug("SSR getSSRPort invoked by {}", currentUserInfo);
        return ssrPortService.readPort();
    }

}
