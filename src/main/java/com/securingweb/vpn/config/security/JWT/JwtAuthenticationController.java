package com.securingweb.vpn.config.security.JWT;


import com.securingweb.vpn.utility.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用于验证 jwt 返回客户端 jwt（json web token）
 */
@Controller
@Description("it returns model-view instead of pure JSON. ")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 获取 客户端来的 username password 使用秘钥加密成 json web token
     */
    @RequestMapping(value = "/jwtAuthenticate", method = RequestMethod.POST)
//    public String createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
    public String createAuthenticationToken(@RequestParam("username") String username, @RequestParam("password") String password, Model model) throws Exception {

//        JwtRequest authenticationRequest = new JwtRequest(username, password);

        authenticate(username, password);

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(username);

        final String token = jwtTokenUtil.generateToken(userDetails);

        model.addAttribute("JWTToken", token);
        //go to home view and then go to home.html
        return "home";
    }

    /**
     * 获取 客户端来的 username password 使用秘钥加密成 json web token
     */
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
