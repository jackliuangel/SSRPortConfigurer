package com.securingweb.vpn.controller;


import com.securingweb.vpn.controller.resolver.UserInfo;
import com.securingweb.vpn.service.SSRPortService;
import com.securingweb.vpn.utility.CommandUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/SSR")
@Slf4j
public class SSRPortController extends AuditableController {

    @Autowired
    SSRPortService ssrPortService;

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

            updatePortAction(currentUserInfo.getName());

            return SSRRestartCommand + "\n\n" + result;
        } catch (Exception e) {
            updatePortFailAction(currentUserInfo.getName());
            return "update port failed ";
        }
    }

    @Cacheable("ssr")
    @GetMapping("/")
    public Integer getSSRPort(UserInfo currentUserInfo) throws Exception {

        readPortAction(currentUserInfo.getName());

        log.debug("SSR getSSRPort invoked by {}", currentUserInfo);
        return ssrPortService.readPort();
    }

}
