package org.tron.albert.liang;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.tron.albert.liang.disruptor.CalSameConsumer;
import org.tron.albert.liang.service.GenNewPkService;

import java.util.concurrent.atomic.AtomicLong;

import static org.tron.albert.liang.service.Sent2ServerService.Sent2Server_counter;

/**
 * @author rafael
 */
@Slf4j
@Component
public class Monitor {

    public static AtomicLong Disruptor_counter = new AtomicLong(0);
    public static AtomicLong Main_counter = new AtomicLong(0);
    public static AtomicLong find_counter = new AtomicLong(0);

    @Scheduled(fixedRate = 5000L)
    public void print() {

        long consumer = CalSameConsumer.CalSameConsumer_count.get();
        long main = GenNewPkService.GenNewPkService_counter.get();
        long find = Sent2Server_counter.get();
        log.info("Monitor: CalSameConsumer  () {},  差值{}", consumer, consumer - Disruptor_counter.get());
        log.info("Monitor: GenNewPkService  () {},  差值{}", main, main - Main_counter.get());
        log.info("Monitor: find_counter     () {},  差值{}", find, find - find_counter.get());
        Disruptor_counter.set(consumer);
        Main_counter.set(main);
        find_counter.set(find);
    }
}
