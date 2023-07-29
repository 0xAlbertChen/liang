package org.tron.albert.liang.config;

import cn.hutool.core.io.FileUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.tron.albert.liang.config.SystemConstants.TRANSFER_PUBLIC_KEY_BASE_64_PATH;

/**
 * @author tieJiang
 * @date 2023/7/3 08:41
 */
@Service
@Slf4j
public class InitService {


    public static String TRANSFER_PRIVATE_KEY_BASE_64 = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALXPXO8slaSIyZL1+1u3npPclJ7eN4VT78rBwsZXA1A2SJtBTBaEDN2mlMXJxOJ2jtYWoqGAcnLzLMEfd20CKp6KvuMVdXvNIKxyV0wsB3VFPCxMlcQmUwwn0JPMJz5XWD9TWQ00J93x9Lu2LSv1hE9dkShm20ypw4g6X2iBDqwPAgMBAAECgYAr7cWbuJ8TmUVzL+IjABPd0C9Mz46EtYo76GNCfyW6zNLwSG+1BQUCzUwnGiBrQ5AxpFh5Q0rkAWOv6gV2Hk7jhXwnG+5fqNC9VrMgJAeAD+GGV+iP2pz8ZoOOHbQgcj98AO373nNG/de3S7/OWSwml8uo7SIpPNcpvM2cyX45wQJBAN8l5O283jbF1QUyy7O+ZYtyCZT7c7cLj/8ytW+s7Xudjd4mazeJ7MAdhkTPZUpEZPIxyLA0XoKGjTeH9zhXGGECQQDQk4CvE8lMkED859zw5RnjcRXwQKQox10P3but8B7M0x32Ti8houoUUzBoT7F5h2iiRUkI8HNVrcANNwbbWVpvAkEAiLzwxllolYmumgvhVRhDoypTAs7+vljAeTG6il8d+xS1nT5Pju3efG7bGwk3WUYF0X9+KbM9gqAuoquXaxe7oQJAROIyAaA7Pvn1PJEmurgvEJpscGgozv7okTDdpthCk8q1fn7bufQf8o520jxflI9P0yemszkWHhm/751YumCMKQJASi3BSfqsB4JsuHH/n9549VBWINZtHQm4nnTyreWoV09vIfKTVHwGh4uRAb7CDiAD8B7ZQhbV6YYSfAP4/UYrOw==";
    public static String TRANSFER_PUBLIC_KEY_BASE_64 = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALXPXO8slaSIyZL1+1u3npPclJ7eN4VT78rBwsZXA1A2SJtBTBaEDN2mlMXJxOJ2jtYWoqGAcnLzLMEfd20CKp6KvuMVdXvNIKxyV0wsB3VFPCxMlcQmUwwn0JPMJz5XWD9TWQ00J93x9Lu2LSv1hE9dkShm20ypw4g6X2iBDqwPAgMBAAECgYAr7cWbuJ8TmUVzL+IjABPd0C9Mz46EtYo76GNCfyW6zNLwSG+1BQUCzUwnGiBrQ5AxpFh5Q0rkAWOv6gV2Hk7jhXwnG+5fqNC9VrMgJAeAD+GGV+iP2pz8ZoOOHbQgcj98AO373nNG/de3S7/OWSwml8uo7SIpPNcpvM2cyX45wQJBAN8l5O283jbF1QUyy7O+ZYtyCZT7c7cLj/8ytW+s7Xudjd4mazeJ7MAdhkTPZUpEZPIxyLA0XoKGjTeH9zhXGGECQQDQk4CvE8lMkED859zw5RnjcRXwQKQox10P3but8B7M0x32Ti8houoUUzBoT7F5h2iiRUkI8HNVrcANNwbbWVpvAkEAiLzwxllolYmumgvhVRhDoypTAs7+vljAeTG6il8d+xS1nT5Pju3efG7bGwk3WUYF0X9+KbM9gqAuoquXaxe7oQJAROIyAaA7Pvn1PJEmurgvEJpscGgozv7okTDdpthCk8q1fn7bufQf8o520jxflI9P0yemszkWHhm/751YumCMKQJASi3BSfqsB4JsuHH/n9549VBWINZtHQm4nnTyreWoV09vIfKTVHwGh4uRAb7CDiAD8B7ZQhbV6YYSfAP4/UYrOw==";

    ;

    @PostConstruct
    public void init() {

        initTransferKey();
    }

    public void initTransferKey() {
        log.info("TRANSFER_PUBLIC_KEY_BASE_64_PATH:{}", TRANSFER_PUBLIC_KEY_BASE_64_PATH);
        List<String> publicKeys = FileUtil.readUtf8Lines(TRANSFER_PUBLIC_KEY_BASE_64_PATH);
        TRANSFER_PUBLIC_KEY_BASE_64 = publicKeys.get(0);
    }

}
