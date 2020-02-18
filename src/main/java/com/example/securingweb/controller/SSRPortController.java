package com.example.securingweb.controller;


import com.example.securingweb.service.SSRPortService;
import com.example.securingweb.utility.CommandUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/SSRPort")
public class SSRPortController {

    @Autowired
    SSRPortService ssrPortService;

    @Value("${SSR.Command}")
    String restartCommand;

    @GetMapping("/set/{portNumber}")
    @ResponseStatus(HttpStatus.CREATED)
    public String updateSSRPort(@PathVariable("portNumber") Integer portNumber) throws Exception {
        ssrPortService.configPort(portNumber);
        String result = CommandUtil.run(restartCommand);

        return result;
    }


    @GetMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Integer getSSRPort() throws Exception {
        return ssrPortService.readPort();

    }

}
