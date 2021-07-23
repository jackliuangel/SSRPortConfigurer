package com.securingweb.vpn.audit;


import com.securingweb.vpn.domain.common.UserAudit;
import com.securingweb.vpn.domain.common.UserAuditRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuditEventHandler {
    @Autowired
    UserAuditRepository userAuditRepository;

    @EventListener
    public void eventHandler(UserAuditEvent userAuditEvent) {

        log.info("event logged: {}", userAuditEvent);

        UserAudit userAuditEntry = UserAudit.builder().action(userAuditEvent.getUserAuditAction())
                                            .userName(userAuditEvent.getUserName())
                                            .build();

        userAuditRepository.save(userAuditEntry);

    }


}
