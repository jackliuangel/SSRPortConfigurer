package com.example.securingweb.controller;


import com.example.securingweb.service.SSRPortService;
import com.example.securingweb.utility.CommandUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/SSR")
public class SSRPortController {

    @Autowired
    SSRPortService ssrPortService;


    @Value("${SSR.Command}")
    String SSRRestartCommand;

    @GetMapping("/set/{portNumber}")
    @ResponseStatus(HttpStatus.CREATED)
    public String updateSSRPort(@PathVariable("portNumber") Integer portNumber) throws Exception {
        ssrPortService.configPort(portNumber);
        String result = CommandUtil.run(SSRRestartCommand);
        return SSRRestartCommand + "\n\n" + result;
    }


    @GetMapping("/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Integer getSSRPort() throws Exception {
        return ssrPortService.readPort();

    }

}
