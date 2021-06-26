package com.securingweb.vpn.controller;


import com.securingweb.vpn.service.SSRPortService;
import com.securingweb.vpn.utility.CommandUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @GetMapping("/set/{portNumber}")
    @ResponseStatus(HttpStatus.CREATED)
    public String updateSSRPort(@PathVariable("portNumber") Integer portNumber) throws Exception {

        log.info("SSR updateV2RayPort");
        ssrPortService.configPort(portNumber);
        String result = CommandUtil.run(SSRRestartCommand);
        return SSRRestartCommand + "\n\n" + result;
    }


    @GetMapping("/")
//    @ResponseStatus(HttpStatus.ACCEPTED)
    public Integer getSSRPort() throws Exception {
        log.info("SSR getSSRPort");
        return ssrPortService.readPort();

    }

}
