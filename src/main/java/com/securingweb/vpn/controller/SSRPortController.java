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
public class SSRPortController {

    @Autowired
    SSRPortService ssrPortService;


    @Value("${SSR.Command}")
    String SSRRestartCommand;

    @CachePut("ssr")
    @GetMapping("/set/{portNumber}")
    @ResponseStatus(HttpStatus.CREATED)
    public String updateSSRPort(@PathVariable("portNumber") Integer portNumber, UserInfo currentUserInfo) throws Exception {

        if (currentUserInfo.getAuthority().contains("admin")) {
            log.info("SSR updateV2RayPort");
            ssrPortService.configPort(portNumber);
            String result = CommandUtil.run(SSRRestartCommand);
            return SSRRestartCommand + "\n\n" + result;
        } else {
            return "you are not admin so can not set SSR port. This info should be only shown to OAuth2 authentication";
        }
    }

    @Cacheable("ssr")
    @GetMapping("/")
    public Integer getSSRPort(UserInfo currentUserInfo) throws Exception {
        log.info("SSR getSSRPort invoked by {}", currentUserInfo);
        return ssrPortService.readPort();
    }

}
