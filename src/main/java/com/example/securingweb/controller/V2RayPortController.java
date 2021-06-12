package com.example.securingweb.controller;


import com.example.securingweb.service.V2RayPortService;
import com.example.securingweb.utility.CommandUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/V2Ray")
@Slf4j
public class V2RayPortController {

    @Autowired
    V2RayPortService v2RayPortService;

    @Value("${V2Ray.Command}")
    String restartCommand;

    @GetMapping("/set/{portNumber}")
    @ResponseStatus(HttpStatus.CREATED)
    public String updateV2RayPort(@PathVariable("portNumber") Integer portNumber) throws Exception {
        v2RayPortService.configPort(portNumber);
        String result = CommandUtil.run(restartCommand);
        return restartCommand+ "\n"+ result;
    }


    @GetMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Integer getV2RayPort() throws Exception {
        log.info("V2Ray getV2RayPort");
        return v2RayPortService.readPort();
    }

}
