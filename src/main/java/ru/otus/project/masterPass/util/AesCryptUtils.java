package ru.otus.project.masterPass.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Slf4j
public class AesCryptUtils {

    public static String encrypt(String plaintext, SecretKey key, int gcmTagLength, byte[] initializationVector) {

        // Create SecretKeySpec
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
        // Create GCMParameterSpec
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(gcmTagLength * 8, initializationVector);

        String cipherText = null;
        try {
            // Get Cipher Instance
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            // Initialize Cipher for ENCRYPT_MODE
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);
            // Perform Encryption
            cipherText = Base64Utils.encodeToString(cipher.doFinal(plaintext.getBytes()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return cipherText;
    }

    public static String decrypt(String cipherText, SecretKey key, int gcmTagLength, byte[] initializationVector) {

        // Create SecretKeySpec
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
        // Create GCMParameterSpec
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(gcmTagLength * 8, initializationVector);

        byte[] decryptedText = new byte[0];
        try {
            // Get Cipher Instance
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            // Initialize Cipher for DECRYPT_MODE
            cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);
            // Perform Decryption
            decryptedText = cipher.doFinal(Base64Utils.decodeToByteArray(cipherText));
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }

        return new String(decryptedText);
    }

}
