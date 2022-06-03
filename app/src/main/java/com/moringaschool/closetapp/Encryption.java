package com.moringaschool.closetapp;

import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;

import java.math.BigInteger;

public class Encryption {
    public static String pbkdf2(String secret, String salt, int iterations, int keyLength) {
        PKCS5S2ParametersGenerator gen = new PKCS5S2ParametersGenerator(new SHA256Digest());
        byte[] secretData = secret.getBytes();
        byte[] saltData = salt.getBytes();
        gen.init(secretData, saltData, iterations);
        byte[] derivedKey = ((KeyParameter)gen.generateDerivedParameters(keyLength * 8)).getKey();
        return toHex(derivedKey);
    }

    private static String toHex(byte[] bytes) {
        BigInteger bi = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "x", bi);
    }


}