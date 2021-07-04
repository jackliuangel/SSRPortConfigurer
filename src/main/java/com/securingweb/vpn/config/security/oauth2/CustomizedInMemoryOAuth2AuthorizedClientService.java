package com.securingweb.vpn.config.security.oauth2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.util.Assert;

import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * extends from
 * @class InMemoryOAuth2AuthorizedClientService
 */
@Slf4j
public final class CustomizedInMemoryOAuth2AuthorizedClientService implements OAuth2AuthorizedClientService {
    private final Map<String, OAuth2AuthorizedClient> authorizedClients = new ConcurrentHashMap<>();
    private final ClientRegistrationRepository clientRegistrationRepository;

     public CustomizedInMemoryOAuth2AuthorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
        Assert.notNull(clientRegistrationRepository, "clientRegistrationRepository cannot be null");
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId, String principalName) {
        Assert.hasText(clientRegistrationId, "clientRegistrationId cannot be empty");
        Assert.hasText(principalName, "principalName cannot be empty");
        ClientRegistration registration = this.clientRegistrationRepository.findByRegistrationId(clientRegistrationId);
        if (registration == null) {
            return null;
        }

        log.info("map this name to UserDetailService principal name: {}", principalName);

        return (T) this.authorizedClients.get(this.getIdentifier(registration, principalName));
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal) {
        Assert.notNull(authorizedClient, "authorizedClient cannot be null");
        Assert.notNull(principal, "principal cannot be null");
        log.info("map this name to UserDetailService principal name and save : {} {}", authorizedClient.getClientRegistration(), principal.getName());
        this.authorizedClients.put(this.getIdentifier(
                authorizedClient.getClientRegistration(), principal.getName()), authorizedClient);
    }

    @Override
    public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
        Assert.hasText(clientRegistrationId, "clientRegistrationId cannot be empty");
        Assert.hasText(principalName, "principalName cannot be empty");
        ClientRegistration registration = this.clientRegistrationRepository.findByRegistrationId(clientRegistrationId);
        if (registration != null) {
            this.authorizedClients.remove(this.getIdentifier(registration, principalName));
        }
    }

    private String getIdentifier(ClientRegistration registration, String principalName) {
        String identifier = "[" + registration.getRegistrationId() + "][" + principalName + "]";
        return Base64.getEncoder().encodeToString(identifier.getBytes());
    }
}
