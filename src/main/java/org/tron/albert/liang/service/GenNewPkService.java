package org.tron.albert.liang.service;


import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tron.albert.liang.events.CustomSpringEventPublisher;
import org.tron.albert.liang.key.KeyPair;

import static org.tron.albert.liang.config.SystemConstants.SAME_CHARACTER_COUNT;

/**
 * @author rafael
 */
@Slf4j
@Service
public class GenNewPkService {

    public static final int FOUR_SAME = 3;
    private final CustomSpringEventPublisher customSpringEventPublisher;

    public GenNewPkService(CustomSpringEventPublisher customSpringEventPublisher) {
        this.customSpringEventPublisher = customSpringEventPublisher;
    }

    private static boolean checkSame(int sameNumber, char[] charArray) {
        boolean same = false;
        for (int j = 0; j < sameNumber; j++) {
            char after = charArray[charArray.length - j - 1];
            char before = charArray[charArray.length - j - 2];
            if (after == before) {
                same = true;
            } else {
                return false;
            }
        }
        return same;
    }

    public void run() {

        for (; ; ) {

            KeyPair keyPair = KeyPair.generate();
            String privateKey = keyPair.toPrivateKey();
            String base58CheckAddress = keyPair.toBase58CheckAddress();

            String lowerCase = base58CheckAddress.toLowerCase();
            char[] lowerCaseCharArray = lowerCase.toCharArray();
            char[] originalCharArray = base58CheckAddress.toCharArray();

            String reverse = StrUtil.reverse(base58CheckAddress);
            String message = reverse + "," + base58CheckAddress + "," + privateKey;
            if (checkSame(FOUR_SAME, originalCharArray)) {
                log.info("FOUR_SAME {} base58CheckAddress: {},{}", FOUR_SAME + 1, reverse, base58CheckAddress);
                customSpringEventPublisher.publishCustomEvent(message);
            } else if (checkSame(SAME_CHARACTER_COUNT - 1, lowerCaseCharArray)) {
                log.info("SAME_CHARACTER_COUNT {} base58CheckAddress: {},{}", SAME_CHARACTER_COUNT, reverse, base58CheckAddress);
                customSpringEventPublisher.publishCustomEvent(message);
            }

        }

    }
}
