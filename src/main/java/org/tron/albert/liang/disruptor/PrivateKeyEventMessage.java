package org.tron.albert.liang.disruptor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rafael
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrivateKeyEventMessage {

    private String base58CheckAddress;
    private String privateKey;

}
