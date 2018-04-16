package com.alexandre.crychat.utilities;

import java.security.SecureRandom;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

public class CryptoServiceProvider {
    byte[] key;

    public CryptoServiceProvider(String seed) {
        try {
            key = getRawKey(seed.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String Encrypt(String msg) throws Exception {
        if(key.length == 0)
            throw new Exception("No key");
        byte[] cipheredMsg;

        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        cipheredMsg = cipher.doFinal(msg.getBytes());
        return new String(cipheredMsg);
    }

    public String Decrypt (String cipheredMsg) throws Exception {
        if(key.length == 0)
            throw new Exception("no key");
        byte[] msg;

        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        msg = cipher.doFinal(cipheredMsg.getBytes());
        return new String(msg);
    }


    private byte[] getRawKey(byte[] _seed) throws Exception {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(_seed);

        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128, random);
        SecretKey key = keyGen.generateKey();

        byte[] rawKey = key.getEncoded();
        return rawKey;
    }
}
