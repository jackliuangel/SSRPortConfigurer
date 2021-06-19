package com.securingweb.vpn;

import com.securingweb.vpn.config.db.CommonDbConfiguration;
import com.securingweb.vpn.config.db.InternalDbConfiguration;
import com.securingweb.vpn.config.security.EncryptedWebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
//        (exclude = {InternalDbConfiguration.class, CommonDbConfiguration.class, EncryptedWebSecurityConfig.class})
@ComponentScan(excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE, classes = {InternalDbConfiguration.class,
        CommonDbConfiguration.class,
        EncryptedWebSecurityConfig.class,
        SecuringWebApplication.class
}))
public class SecuringWebApplicationWithoutDB {

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(SecuringWebApplicationWithoutDB.class, args);
    }

}
