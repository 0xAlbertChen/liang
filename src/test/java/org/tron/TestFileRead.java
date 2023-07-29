package org.tron;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.tron.albert.liang.service.Sent2ServerService;

import java.util.List;

@Slf4j
public class TestFileRead {

    public static final String FileName = "/Users/rafael/albertCode/liang-project/liang/output/ppppk copy.csv";

    public static void main(String[] args) {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String resourcesDirectory = classLoader.getResource("").getPath();
        log.info("Resources Directory: " + resourcesDirectory);
        List<String> strings = FileUtil.readUtf8Lines(FileName);
        strings.forEach(line -> {
            Sent2ServerService sent2ServerService = new Sent2ServerService();
            String[] split = line.split(",");
            if (split.length == 2) {
                sent2ServerService.sendToServer(line);
            } else if (split.length == 3) {
                sent2ServerService.sendToServer(split[1] + "," + split[2]);
            }
            log.info(line);
        });


    }
}
