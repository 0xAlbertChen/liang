package org.tron.albert.liang.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.tron.albert.liang.events.CustomSpringEventPublisher;
import org.tron.albert.liang.key.KeyPair;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author rafael
 */
@Component
@Slf4j
public class CalSameDisruptorManager {

    private final CustomSpringEventPublisher customSpringEventPublisher;
    private RingBuffer<PrivateKeyEventMessage> ringBuffer;

    public CalSameDisruptorManager(CustomSpringEventPublisher customSpringEventPublisher) {
        this.customSpringEventPublisher = customSpringEventPublisher;
    }


    @PostConstruct
    /**
     * 方法二
     * 测试通过
     */
    public void init() {
        log.info("初始化Disruptor开始。。。");
        //1 创建RingBuffer
        ringBuffer =
                RingBuffer.create(ProducerType.MULTI,
                        () -> new PrivateKeyEventMessage(),
                        1024 * 256,
                        new YieldingWaitStrategy());

        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        //3 创建多个消费者数组:
        CalSameConsumer[] calSameConsumers = new CalSameConsumer[2];
        for (int i = 0; i < calSameConsumers.length; i++) {
            calSameConsumers[i] = new CalSameConsumer(customSpringEventPublisher);
        }

        WorkerPool<PrivateKeyEventMessage> workerPool = new WorkerPool<>(
                ringBuffer,
                sequenceBarrier,
                new EventExceptionHandler(),
                calSameConsumers);

        ThreadFactory threadFactory = r -> new Thread(r, "MESSAGE-PUBLISH-THREAD");
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());

        //6 启动workerPool
        workerPool.start(Executors.newFixedThreadPool(2));


        log.info("初始化Disruptor结束。。。");
    }


    public void sendData(PrivateKeyEventMessage event) {
        long sequence = ringBuffer.next();
        try {
            PrivateKeyEventMessage privateKeyEventMessage = ringBuffer.get(sequence);
            privateKeyEventMessage.setBase58CheckAddress(event.getBase58CheckAddress());
            privateKeyEventMessage.setPrivateKey(event.getPrivateKey());
        } finally {
            ringBuffer.publish(sequence);
        }
    }


    public void run() {

        for (; ; ) {

            KeyPair keyPair = KeyPair.generate();
            String privateKey = keyPair.toPrivateKey();
            String base58CheckAddress = keyPair.toBase58CheckAddress();

            long sequence = ringBuffer.next();
            try {
                PrivateKeyEventMessage privateKeyEventMessage = ringBuffer.get(sequence);
                privateKeyEventMessage.setBase58CheckAddress(base58CheckAddress);
                privateKeyEventMessage.setPrivateKey(privateKey);
            } finally {
                ringBuffer.publish(sequence);
            }

        }

    }

    static class EventExceptionHandler implements ExceptionHandler<PrivateKeyEventMessage> {
        public void handleEventException(Throwable ex, long sequence, PrivateKeyEventMessage event) {
            log.error(ex.getMessage(), ex);
            log.info("sequence:{},event:{}", sequence, event);
        }

        public void handleOnStartException(Throwable ex) {
            log.error(ex.getMessage(), ex);
        }

        public void handleOnShutdownException(Throwable ex) {
            log.error(ex.getMessage(), ex);
        }
    }

}
