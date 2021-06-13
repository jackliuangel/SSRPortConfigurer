package com.securingweb.vpn.controller;


import com.securingweb.vpn.service.V2RayPortService;
import com.securingweb.vpn.utility.CommandUtil;
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

//    @Value("${V2Ray.Command}")
//    String restartCommand;

    @Value("${V2Ray.Command}")
    String V2RayRestartCommand;


    @GetMapping("/set/{portNumber}")
    @ResponseStatus(HttpStatus.CREATED)
    public String updateV2RayPort(@PathVariable("portNumber") Integer portNumber) throws Exception {

        log.info("V2Ray updateV2RayPort");
        v2RayPortService.configPort(portNumber);
        String result = CommandUtil.run(V2RayRestartCommand);
//        StringBuilder stringBuilder = new StringBuilder(restartCommand);
//        stringBuilder.append(result);
        return V2RayRestartCommand + "\n\n" + result;
    }


    @GetMapping("/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Integer getV2RayPort() throws Exception {
        log.info("V2Ray getV2RayPort");
        return v2RayPortService.readPort();
    }

}
