package br.com.tlmacedo.cafeperfeito.service;

import org.mindrot.jbcrypt.BCrypt;

public class ServiceCryptografia {

    public static String encrypt(String senhaSimples) {
        return BCrypt.hashpw(senhaSimples, BCrypt.gensalt(10));
    }

    public static boolean senhaValida(String senhaSimples, String senhaBD) {
        System.out.println(String.format("senhaSimples: %s", senhaSimples));
        System.out.println(String.format("senhaBD: %s", senhaBD));
        if (BCrypt.checkpw(senhaSimples, senhaBD)) {
            System.out.println("senha validada!!!!!!!!");
            return true;
        } else {
            System.out.println("senha inválida");
            return false;
        }
    }

    //    public static String encrypt(String senhaSimples) {
//        try {
//            IvParameterSpec iv = new IvParameterSpec(CRYPT_INITVECTOR.getBytes("UTF-8"));
//            SecretKeySpec skeySpec = new SecretKeySpec(CRYPT_PALAVRA_CHAVE.getBytes("UTF-8"), "AES");
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
//            byte[] encrypted = cipher.doFinal(senhaSimples.getBytes());
//            return Base64.getEncoder().encodeToString(encrypted);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }


//    public static String decrypt(String senhaEncrypt) {
//        try {
//            IvParameterSpec iv = new IvParameterSpec(CRYPT_INITVECTOR.getBytes("UTF-8"));
//            SecretKeySpec skeySpec = new SecretKeySpec(CRYPT_PALAVRA_CHAVE.getBytes("UTF-8"), "AES");
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
//            byte[] original = cipher.doFinal(Base64.getDecoder().decode(senhaEncrypt));
//            return new String(original);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }
}
