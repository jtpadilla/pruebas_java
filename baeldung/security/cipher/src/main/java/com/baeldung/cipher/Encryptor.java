package com.baeldung.cipher;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;

public class Encryptor {

    /////////////////////////////////////////////////////////////
    // Encriptacion mediante una clave
    //
    // La clave originalmente era una cadena de texto de la cual
    // se extrajeron los correspondientes bytes.
    // Estos bytes son utilizados para construir un
    // 'SecretKeySpec' el cual es una implementacion
    // de 'SecretKey'.
    //
    // SecretKey sk = new SecretKeySpec("clave".getBytes(), "AES");
    //
    // Este SecretKey se puede utilizar tanto en encriptacion
    // y en desencriptacion.
    //
    //   cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    //   cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    /////////////////////////////////////////////////////////////

    public byte[] encryptMessage(byte[] message, byte[] keyBytes)
            throws InvalidKeyException,
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            BadPaddingException,
            IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(message);
    }

    public byte[] decryptMessage(byte[] encryptedMessage, byte[] keyBytes)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            BadPaddingException,
            IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(encryptedMessage);
    }

    /////////////////////////////////////////////////////////////
    // Encriptacion mediante un certificado
    // (entiendo que la clave publica del certificado)
    /////////////////////////////////////////////////////////////

    public byte[] encryptMessage(byte[] message, Certificate publicKeyCertificate)
            throws InvalidKeyException,
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            BadPaddingException,
            IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKeyCertificate);
        return cipher.doFinal(message);
    }

}
