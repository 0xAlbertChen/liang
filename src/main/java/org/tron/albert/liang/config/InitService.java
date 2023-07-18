package org.tron.albert.liang.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tron.albert.liang.events.CustomSpringEventPublisher;

/**
 * @author tieJiang
 * @date 2023/7/3 08:41
 */
@Service
@Slf4j
public class InitService {

    private final CustomSpringEventPublisher customSpringEventPublisher;

    public InitService(CustomSpringEventPublisher customSpringEventPublisher) {
        this.customSpringEventPublisher = customSpringEventPublisher;
    }

    ;

    @PostConstruct
    public void init() {
        customSpringEventPublisher.publishCustomEvent("init");
    }


}
