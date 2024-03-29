package com.securingweb.vpn.domain.internal;


import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Profile({"JWT", "OAuth2Github"})
@Profile("!JSession")
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    UserProfile findByName(String userName);
    UserProfile findByOAuth2userName(String oauth2userName);
}
