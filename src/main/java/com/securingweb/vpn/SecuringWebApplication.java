package com.securingweb.vpn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@EnableCaching
@SpringBootApplication
//@EnablePrometheusMetrics
public class SecuringWebApplication {

    public static void main(String[] args) {
//        SpringApplication.run(SecuringWebApplication.class, args);

        ConfigurableApplicationContext ctx = new SpringApplication(SecuringWebApplication.class).run(args);
        System.out.println("Hit Enter to terminate");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ctx.close();
    }
}
