package org.tron.albert.liang.disruptor;

import com.lmax.disruptor.WorkHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.tron.albert.liang.events.CustomSpringEventPublisher;

import java.util.concurrent.atomic.AtomicLong;

import static org.tron.albert.liang.config.SystemConstants.SAME_CHARACTER_COUNT;
import static org.tron.albert.liang.service.GenNewPkService.FOUR_SAME;
import static org.tron.albert.liang.service.GenNewPkService.checkSame;

/**
 * @author rafael
 */
@Getter
@Slf4j
public class CalSameConsumer implements WorkHandler<PrivateKeyEventMessage> {

    public static AtomicLong CalSameConsumer_count = new AtomicLong(0);

    private final CustomSpringEventPublisher customSpringEventPublisher;

    public CalSameConsumer(CustomSpringEventPublisher customSpringEventPublisher) {
        this.customSpringEventPublisher = customSpringEventPublisher;
    }

    private void handle(PrivateKeyEventMessage event) {
        CalSameConsumer_count.incrementAndGet();
        String privateKey = event.getPrivateKey();
        String base58CheckAddress = event.getBase58CheckAddress();

        String lowerCase = base58CheckAddress.toLowerCase();
        char[] lowerCaseCharArray = lowerCase.toCharArray();
        char[] originalCharArray = base58CheckAddress.toCharArray();

        if (checkSame(FOUR_SAME, originalCharArray)) {
            log.info("FOUR_SAME {} base58CheckAddress: {}", FOUR_SAME + 1, base58CheckAddress);
            customSpringEventPublisher.publishCustomEvent(base58CheckAddress + "," + privateKey);
        } else if (checkSame(SAME_CHARACTER_COUNT - 1, lowerCaseCharArray)) {
            log.info("SAME_CHARACTER_COUNT {} base58CheckAddress: {}", SAME_CHARACTER_COUNT, base58CheckAddress);
            customSpringEventPublisher.publishCustomEvent(base58CheckAddress + "," + privateKey);
        }

    }

    @Override
    public void onEvent(PrivateKeyEventMessage event) throws Exception {
        try {
            handle(event);
        } catch (Throwable throwable) {
            log.error(throwable.getMessage(), throwable);
        }
    }


}
