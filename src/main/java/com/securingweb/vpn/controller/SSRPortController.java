package com.securingweb.vpn.controller;


import com.securingweb.vpn.audit.UserAuditAction;
import com.securingweb.vpn.audit.UserAuditEvent;
import com.securingweb.vpn.controller.resolver.UserInfo;
import com.securingweb.vpn.service.SSRPortService;
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
@RequestMapping("/SSR")
@Slf4j
public class SSRPortController {

    @Autowired
    SSRPortService ssrPortService;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Value("${SSR.Command}")
    String SSRRestartCommand;

    @CachePut("ssr")
    @GetMapping("/set/{portNumber}")
    @ResponseStatus(HttpStatus.CREATED)
    public String updateSSRPort(@PathVariable("portNumber") Integer portNumber, UserInfo currentUserInfo) {
        try {
            log.debug("SSR updateV2RayPort");
            ssrPortService.configPort(portNumber);
            String result = CommandUtil.run(SSRRestartCommand);

            applicationEventPublisher.publishEvent(new UserAuditEvent(currentUserInfo.getName(), UserAuditAction.UPDATE_PORT_SUCCESSFUL));

            return SSRRestartCommand + "\n\n" + result;
        } catch (Exception e) {
            applicationEventPublisher.publishEvent(new UserAuditEvent(currentUserInfo.getName(), UserAuditAction.UPDATE_PORT_FAIL));
            return "update port failed ";
        }
    }

    @Cacheable("ssr")
    @GetMapping("/")
    public Integer getSSRPort(UserInfo currentUserInfo) throws Exception {

        applicationEventPublisher.publishEvent(new UserAuditEvent(currentUserInfo.getName(), UserAuditAction.READ_PORT));

        log.debug("SSR getSSRPort invoked by {}", currentUserInfo);
        return ssrPortService.readPort();
    }

}
