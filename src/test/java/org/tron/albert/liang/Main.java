package org.tron.albert.liang;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.FixedPointCombMultiplier;
import org.tron.albert.liang.crypto.tuwenitypes.Bytes;
import org.tron.albert.liang.crypto.tuwenitypes.Bytes32;
import org.tron.albert.liang.key.KeyPair;
import org.tron.albert.liang.utils.Base58Check;

import java.math.BigInteger;
import java.util.Arrays;

import static org.tron.albert.liang.crypto.SECP256K1.CURVE;

@Slf4j
public class Main {

    public static Keccak.Digest256 digest = new Keccak.Digest256();
    public static byte[] rawAddr = new byte[21];

    public static void main(String[] args) {

        String hexPrivateKey = "0000000000000000000000000000000000000000000000000000000000000002";
        log.info(hexPrivateKey.length() + "");
        String base58Address = toBase58Address(hexPrivateKey);
        log.info("base58Address:{}", base58Address);

        KeyPair keyPair = new KeyPair(hexPrivateKey);
        log.info("base58Address:{}", keyPair.toBase58CheckAddress());


        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {

//        KeyPair keyPair1 = KeyPair.generate();
//            keyPair1.toBase58CheckAddress();
//        log.info("time:{}", (System.nanoTime() - start));
//            hexPrivateKey = HexUtil.encodeHexStr(RandomUtil.randomBytes(32));
//            base58Address = toBase58Address(hexPrivateKey);


        }

//        log.info("time:{}", (System.nanoTime() - start));
        log.info("time:{}", (System.currentTimeMillis() - start));
    }

    private static String toBase58Address(String hexPrivateKey) {
        Bytes32 bytes32 = Bytes32.fromHexString(hexPrivateKey);
        BigInteger privKey = bytes32.toUnsignedBigInteger();
        if (privKey.bitLength() > CURVE.getN().bitLength()) {
            privKey = privKey.mod(CURVE.getN());
        }

        final ECPoint point = new FixedPointCombMultiplier().multiply(CURVE.getG(), privKey);
        Bytes wrap = Bytes.wrap(Arrays.copyOfRange(point.getEncoded(false), 1, 65));

        digest.update(wrap.toArray(), 0, 64);
        byte[] raw = digest.digest();
        rawAddr[0] = 0x41;
        System.arraycopy(raw, 12, rawAddr, 1, 20);
        String base58Address = Base58Check.bytesToBase58(rawAddr);
        return base58Address;
    }
}
