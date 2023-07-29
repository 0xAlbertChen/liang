package org.tron.albert.liang.service;


import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import cn.hutool.system.SystemUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class Sent2ServerService {


    public static String TRANSFER_PUBLIC_KEY_BASE_64 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC1z1zvLJWkiMmS9ftbt56T3JSe3jeFU+/KwcLGVwNQNkibQUwWhAzdppTFycTido7WFqKhgHJy8yzBH3dtAiqeir7jFXV7zSCscldMLAd1RTwsTJXEJlMMJ9CTzCc+V1g/U1kNNCfd8fS7ti0r9YRPXZEoZttMqcOIOl9ogQ6sDwIDAQAB";


    public static final String SOURCE = SystemUtil.getHostInfo().getAddress();
    ;

    public static final String URL_SAVE = "http://address.gasfree.cc/address/save";

    //    public static final String URL_SAVE =  "http://127.0.0.1:8888/address/save";


    public void sendToServer(String args) {

        String address = args;
        String original = address + "," + SOURCE;

        String data = new RSA(null, TRANSFER_PUBLIC_KEY_BASE_64).encryptBase64(StrUtil.bytes(original), KeyType.PublicKey);
        String request = request(URL_SAVE, data);
        log.info("request:{}", request);

    }

    private String request(String url, String data) {
        Map<String, Object> request = new HashMap<>();
        request.put("data", data);

        String post = HttpRequest.post(url).body(JSONUtil.toJsonStr(request)).execute().body();
        log.info("post:{}", post);
        return post;
    }
}
