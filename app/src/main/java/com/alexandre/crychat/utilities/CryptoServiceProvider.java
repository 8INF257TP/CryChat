package com.alexandre.crychat.utilities;

import java.security.SecureRandom;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

public class CryptoServiceProvider {
    private SecretKey key;

    /**
     *
     * @param password The passphrase used to generate the AES key
     */
    public CryptoServiceProvider(String password) {
        try {
            key = getKey(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param msg The message to encrypt
     * @return Encrypted message
     * @throws Exception
     */
    public byte[] Encrypt(String msg) throws Exception {
        if(key == null)
            throw new Exception("No key");

        key = getKey("test");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        int block = cipher.getBlockSize();
        int length = msg.length();

        while(length%block != 0)
        {
            msg = msg.concat(" ");
            length = msg.length();
        }

        return cipher.doFinal(msg.getBytes());
    }

    /**
     *
     * @param cipheredMsg The crypted message
     * @return A string representing the initial message
     * @throws Exception
     */
    public String Decrypt (byte[] cipheredMsg) throws Exception {
        if(key == null)
            throw new Exception("no key");
        byte[] msg;

        key = getKey("test");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        msg = cipher.doFinal(cipheredMsg);
        return new String(msg);
    }

    private SecretKey getKey(String password) throws Exception {
        return new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
                .generateSecret(new PBEKeySpec(password.toCharArray(), "salt".getBytes(), 10, 128))
                .getEncoded(), "AES");
    }
}
