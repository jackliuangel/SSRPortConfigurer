package com.example.securingweb;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
public class FuncationalTests {


    @Test
    public void testTry() {
        try {
            throw new RuntimeException("runtime Exception");
        } catch (Exception exp) {
            System.err.println("return in catch");
            return;
        } finally {
            System.err.println("return in finally");
        }
    }


    @Test
    public void testEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("1234"));


    }


}
