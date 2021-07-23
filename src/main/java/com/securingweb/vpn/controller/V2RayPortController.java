package com.securingweb.vpn.controller;


import com.securingweb.vpn.audit.UserAuditAction;
import com.securingweb.vpn.audit.UserAuditEvent;
import com.securingweb.vpn.controller.resolver.UserInfo;
import com.securingweb.vpn.service.V2RayPortService;
import com.securingweb.vpn.utility.CommandUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/V2Ray")
@Slf4j
public class V2RayPortController {

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    V2RayPortService v2RayPortService;

    @Value("${V2Ray.Command}")
    String V2RayRestartCommand;

    @CachePut("v2Ray")
    @GetMapping("/set/{portNumber}")
    @ResponseStatus(HttpStatus.CREATED)
    public String updateV2RayPort(@PathVariable("portNumber") Integer portNumber, UserInfo currentUserInfo) throws Exception {
        if (currentUserInfo.getAuthority().contains("admin")) {
            log.info("V2Ray updateV2RayPort");
            v2RayPortService.configPort(portNumber);
            String result = CommandUtil.run(V2RayRestartCommand);

            applicationEventPublisher.publishEvent(new UserAuditEvent(currentUserInfo.getName(), UserAuditAction.UPDATEPORT));

            return V2RayRestartCommand + "\n\n" + result;
        } else {
            return "you are not admin so can not set V2Ray port. This info should be only shown to OAuth2 authentication";
        }
    }

    @Cacheable("v2Ray")
    @GetMapping("/")
    public Integer getV2RayPort(UserInfo currentUserInfo) throws Exception {

        applicationEventPublisher.publishEvent(new UserAuditEvent(currentUserInfo.getName(), UserAuditAction.READPORT));

        log.info("V2Ray getSSRPort invoked by {}", currentUserInfo);
        return v2RayPortService.readPort();
    }
}
