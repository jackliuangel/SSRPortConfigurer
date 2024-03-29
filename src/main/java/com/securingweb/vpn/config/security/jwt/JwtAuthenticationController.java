package com.securingweb.vpn.config.security.jwt;


import com.securingweb.vpn.utility.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用于验证 jwt 返回客户端 jwt（json web token）
 */
@Profile("JWT")
@Controller
@Description("it returns model-view instead of pure JSON. ")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 获取 客户端来的 username password 使用秘钥加密成 json web token
     */
    @RequestMapping(value = "/jwtAuthenticate", method = RequestMethod.POST)
    public String createAuthenticationToken(@RequestParam("username") String username, @RequestParam("password") String password, Model model) throws Exception {

        //invokes loadUserDetails and PasswordEncoder to check the username and password
        Authentication authentication = authenticate(username, password);

        final String token = jwtTokenUtil.generateToken(authentication.getName());

        model.addAttribute("name", "Bearer " + token); //easy to copy paste in Postman
        //go to home view and then go to loginSuccess.html with model
        /**
         * 这种跳转没有经过DispatchServlet
         * 所以不需要security check filter
         * @jwtRequestFilter
         * 如果在defaultSuccessUrl跳转， 需要在header上加上JWT才能通过需要security check filter
         */
        return "loginSuccess";
    }

    /**
     * 获取 客户端来的 username password 使用秘钥加密成 json web token
     */
    private Authentication authenticate(String username, String password) throws Exception {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
