package com.securingweb.vpn;

import com.securingweb.vpn.controller.SSRPortController;
import com.securingweb.vpn.domain.internal.UserProfileRepository;
import lombok.var;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @SpringBootTest by default starts searching in the current package of
 * the test class and then searches upwards through the package structure,
 * looking for a class annotated with @SpringBootConfiguration from which it
 * then reads the configuration to create an application context.
 * This class is usually our main application class since the
 * @SpringBootApplication annotation includes the
 * @SpringBootConfiguration annotation. It then creates an application context very
 * similar to the one that would be started in a production environment.
 */
@SpringBootTest
@ActiveProfiles(profiles = {"JWT"})
class WebApplicationTests {

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    SSRPortController ssrPortController;

    @Test
    void contextLoads() {
        assertThat(ssrPortController).isNotNull();
        assertThat(userProfileRepository).isNotNull();
    }

    @Test
    void testDB() {
        var list = userProfileRepository.findAll();
        assertThat(list.size()).isGreaterThan(1);
    }
}
