package br.com.cafeperfeito.sidtmcafe.service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import static br.com.cafeperfeito.sidtmcafe.interfaces.Constants.CRYPT_INITVECTOR;
import static br.com.cafeperfeito.sidtmcafe.interfaces.Constants.CRYPT_PALAVRA_CHAVE;

public class Cryptografica {

    public static String encrypt(String senhaSimples) {
        try {
            IvParameterSpec iv = new IvParameterSpec(CRYPT_INITVECTOR.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(CRYPT_PALAVRA_CHAVE.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(senhaSimples.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String senhaEncrypt) {
        try {
            IvParameterSpec iv = new IvParameterSpec(CRYPT_INITVECTOR.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(CRYPT_PALAVRA_CHAVE.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.getDecoder().decode(senhaEncrypt));
            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
