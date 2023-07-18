package org.tron.albert.liang.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author rafael
 */
@Slf4j
@Component
public class CustomSpringEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public CustomSpringEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishCustomEvent(final String message) {
        log.info("Publishing custom event. ");
        CustomSpringEvent customSpringEvent = new CustomSpringEvent(this, message);
        applicationEventPublisher.publishEvent(customSpringEvent);
    }
}
