package org.tron.albert.liang;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.tron.albert.liang.disruptor.CalSameDisruptorManager;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * @author rafael
 */
@SpringBootApplication
@Slf4j
public class Application {


    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        Environment env = ctx.getEnvironment();
        String[] activeProfiles = env.getActiveProfiles();
        log.info("##################################################");
        log.info("startSuccess : " + Arrays.toString(activeProfiles));
        log.info("##################################################");
        log.info("user.dir : " + System.getProperty("user.dir"));
        log.info("##################################################");

        try {
            log.info("\n----------------------------------------------------------\n\t" +
                            "Application '{}' is running! Access URLs:\n\t" +
                            "Local: \t\thttp://localhost:{}\n\t" +
                            "External: \thttp://{}:{}\n\t" +
                            "Doc: \thttp://{}:{}/wallet-api/doc.html\n" +
                            "----------------------------------------------------------",
                    env.getProperty("spring.application.name"),
                    env.getProperty("server.port"),
                    InetAddress.getLocalHost().getHostAddress(),
                    env.getProperty("server.port"),
                    InetAddress.getLocalHost().getHostAddress(),
                    env.getProperty("server.port"));
        } catch (UnknownHostException e) {
            log.error("UnknownHostException" + e.getMessage(), e);
        }


        CalSameDisruptorManager genNewPkService = ctx.getBean(CalSameDisruptorManager.class);
        genNewPkService.run();
//        GenNewPkService genNewPkService = ctx.getBean(GenNewPkService.class);
//        genNewPkService.run();

    }

}
