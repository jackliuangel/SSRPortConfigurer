package com.securingweb.vpn.config.security.JWT;


import com.securingweb.vpn.config.security.ApplicationJdbcUserDetailsService;
import com.securingweb.vpn.utility.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

/**
 * 用于验证 jwt 返回客户端 jwt（json web token）
 * */
@RestController
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 获取 客户端来的 username password 使用秘钥加密成 json web token
     * */
    @RequestMapping(value = "/jwtAuthenticate", method = RequestMethod.POST)
//    public String createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
    public String createAuthenticationToken(@RequestParam("username") String username, @RequestParam("password") String password) throws Exception {

        JwtRequest authenticationRequest = new JwtRequest(username,password);

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return token;
    }

    /**
     *  获取 客户端来的 username password 使用秘钥加密成 json web token
     * */
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
