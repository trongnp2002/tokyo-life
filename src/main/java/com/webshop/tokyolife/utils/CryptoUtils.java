package com.webshop.tokyolife.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

@Slf4j
public class CryptoUtils {

    public static String encryptObject(Object object, String secretKey) {
        try {
            Key key = new SecretKeySpec(secretKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] objectBytes = serialize(object);
            byte[] encryptedBytes = cipher.doFinal(objectBytes);
            String base64 = Base64.getEncoder().encodeToString(encryptedBytes).replace("+","%2B");
            base64 = base64.replace("/","%2F");
            return base64.replace("=","%3D");
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return null;
    }

    public static Object decryptObject(String encryptedData, String secretKey) {
        try {
            encryptedData = encryptedData.replace("%2B","+");
            encryptedData = encryptedData.replace("%2F","/");
            encryptedData = encryptedData.replace("%3D","=");
            Key key = new SecretKeySpec(secretKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return deserialize(decryptedBytes);
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

    public static byte[] serialize(Object object) {
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
            objectStream.writeObject(object);
            return byteStream.toByteArray();
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectStream = new ObjectInputStream(byteStream);
        return objectStream.readObject();
    }

}
