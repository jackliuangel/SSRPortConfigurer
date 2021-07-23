package com.securingweb.vpn.audit;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.securingweb.vpn.config.JacksonConfiguration;
import com.securingweb.vpn.domain.common.UserAudit;
import com.securingweb.vpn.domain.common.UserAuditRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Profile("!JSession")
@Component
public class AuditEventHandler {
    @Autowired
    UserAuditRepository userAuditRepository;

    private ObjectMapper objectMapper = JacksonConfiguration.OBJECT_MAPPER;

    @EventListener
    public void eventHandler(UserAuditEvent userAuditEvent) throws JsonProcessingException {

        log.info("event logged: {}", objectMapper.writeValueAsString(userAuditEvent));

        UserAudit userAuditEntry = UserAudit.builder().action(userAuditEvent.getUserAuditAction())
                                            .userName(userAuditEvent.getUserName())
                                            .build();

        userAuditRepository.save(userAuditEntry);
    }
}
