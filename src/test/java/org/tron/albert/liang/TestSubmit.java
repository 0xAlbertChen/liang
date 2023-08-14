package org.tron.albert.liang;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.tron.albert.liang.config.InitService;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import static org.tron.albert.liang.config.InitService.TRANSFER_PUBLIC_KEY_BASE_64;

@Slf4j
public class TestSubmit {


    public static final String URL_SAVE = "http://address.gasfree.cc/address/save";
    //    public static final String URL_SAVE =  "http://127.0.0.1:8888/address/save";
    public static final String URL_GET = "http://127.0.0.1:9999/address/get";


    public static void main(String[] args) {

        InitService initService = new InitService();
        initService.initTransferKey();

        String address = "1,1";
        String original = address + ",1";

        String data = new RSA(null, TRANSFER_PUBLIC_KEY_BASE_64).encryptBase64(StrUtil.bytes(original), KeyType.PublicKey);
        String request = request(URL_SAVE, data);

        log.info("request:{}", request);
//        String request2 = request(URL_GET,address);
//        log.info("request2:{}", request2);

    }

    private static String request(String url, String data) {
        Map<String, Object> request = new HashMap<>();
        request.put("data", data);

        String post = HttpRequest.post(url).body(JSONUtil.toJsonStr(request)).execute().body();
        log.info("post:{}", post);
        return post;
    }

    public static String encrypt(String data) {
        RSA rsa = new RSA(null, TRANSFER_PUBLIC_KEY_BASE_64);
        String base64 = rsa.encryptBase64(StrUtil.bytes(data), KeyType.PublicKey);
        return base64;
    }


    public static void genKeyPair() {


        RSA rsa = new RSA();
//获得私钥
        PrivateKey privateKey = rsa.getPrivateKey();
        String privateKeyBase64 = rsa.getPrivateKeyBase64();
        log.info("privateKeyBase64:{}", privateKeyBase64);

//获得公钥
        PublicKey publicKey = rsa.getPublicKey();
        String publicKeyBase64 = rsa.getPublicKeyBase64();
        log.info("publicKeyBase64:{}", publicKeyBase64);
    }
}
