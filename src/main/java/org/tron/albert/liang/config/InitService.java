package org.tron.albert.liang.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tron.albert.liang.service.GenNewPkService;

/**
 * @author tieJiang
 * @date 2023/7/3 08:41
 */
@Service
@Slf4j
public class InitService {

    private final GenNewPkService genNewPkService;

    public InitService(GenNewPkService genNewPkService) {
        this.genNewPkService = genNewPkService;
    }

    ;

    @PostConstruct
    public void init() {
    }


}
