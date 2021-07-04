package com.securingweb.vpn.config.security.handler;

import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Profile("JSession")
@Controller
public class JSessionLoginController {

    @GetMapping("/loginSuccess")
    public String getLoginInfo(Model model, UsernamePasswordAuthenticationToken authentication) {
        model.addAttribute("name", authentication.getName());
        return "loginSuccess";
    }
}


