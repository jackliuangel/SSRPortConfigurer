package com.securingweb.vpn;

import com.securingweb.vpn.domain.common.UserAuditRepository;
import com.securingweb.vpn.domain.internal.UserProfileRepository;
import com.securingweb.vpn.service.SSRPortService;
import com.securingweb.vpn.service.V2RayPortService;
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
    UserAuditRepository userAuditRepository;

    @Autowired
    SSRPortService ssrPortService;

    @Autowired
    V2RayPortService v2RayPortService;

    @Test
    void contextLoads() {
        assertThat(ssrPortService).isNotNull();
        assertThat(v2RayPortService).isNotNull();
        assertThat(userProfileRepository).isNotNull();
        assertThat(userAuditRepository).isNotNull();
    }

    @Test
    void testDB() {
        var userProfiles = userProfileRepository.findAll();
        assertThat(userProfiles.size()).isGreaterThan(1);

        var userAudits = userAuditRepository.findAll();
        assertThat(userAudits.size()).isGreaterThan(1);
    }
}
