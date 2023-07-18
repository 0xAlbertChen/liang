package org.tron.albert.liang.events;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.tron.albert.liang.config.SystemConstants;

import java.io.File;

/**
 * @author rafael
 */
@Slf4j
@Component
public class CustomSpringEventListener implements ApplicationListener<CustomSpringEvent> {
    @Override
    public void onApplicationEvent(CustomSpringEvent event) {
        File path = new File(SystemConstants.OUTPUT_PATH);

        FileUtil.appendString(event.getMessage() + "\n", path, "UTF-8");
    }
}
