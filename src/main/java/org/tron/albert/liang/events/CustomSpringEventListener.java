package org.tron.albert.liang.events;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.tron.albert.liang.config.SystemConstants;
import org.tron.albert.liang.service.Sent2ServerService;

import java.io.File;

import static org.tron.albert.liang.config.SystemConstants.SEND_TO_SERVER;

/**
 * @author rafael
 */
@Slf4j
@Component
public class CustomSpringEventListener implements ApplicationListener<CustomSpringEvent> {


    private final Sent2ServerService sent2ServerService;

    public CustomSpringEventListener(Sent2ServerService sent2ServerService) {
        this.sent2ServerService = sent2ServerService;
    }



    @Override
    public void onApplicationEvent(CustomSpringEvent event) {
        File path = new File(SystemConstants.OUTPUT_PATH);

        String message = event.getMessage();
        FileUtil.appendString(message + "\n", path, "UTF-8");
        try {
            if (SEND_TO_SERVER) {
                return;
            }
            sent2ServerService.sendToServer(message);
        } catch (Exception e) {
            log.error("Error sending to server: {}" + e.getMessage(), e);
        }
    }
}
