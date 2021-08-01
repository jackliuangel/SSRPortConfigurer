package com.securingweb.vpn.config.security.jsession;

import com.securingweb.vpn.domain.internal.UserProfile;
import com.securingweb.vpn.domain.internal.UserProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Profile("JSessionH2")
@Slf4j
@Component
public class DBInitialization {
    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    PasswordEncoder encoder;

    @Bean
    public void initialize() {

        UserProfile jack = UserProfile.builder()
                                      .name("jack")
                                      .password(encoder.encode("603"))
                                      .authority("admin")
                                      .build();

        UserProfile jason = UserProfile.builder()
                                       .name("jason")
                                       .password(encoder.encode("603"))
                                       .authority("admin")
                                       .build();

        UserProfile angel = UserProfile.builder()
                                       .name("angel")
                                       .password(encoder.encode("603"))
                                       .authority("viewer")
                                       .build();

        List userProfiles = new ArrayList();
        userProfiles.add(jack);
        userProfiles.add(jason);
        userProfiles.add(angel);

        userProfileRepository.saveAll(userProfiles);
    }
}



