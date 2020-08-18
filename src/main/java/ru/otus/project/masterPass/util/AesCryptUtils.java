package ru.otus.project.masterPass.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Slf4j
public class AesCryptUtils {

    public static void main(String[] args) {
        SecretKey key = new SecretKeySpec ("DtnxbyybrjdL;il2v'y98765not32USE".getBytes(), 0, "DtnxbyybrjdL;il2v'y98765not32USE".getBytes().length, "AES");
        byte[] initializationVector = "123456789012".getBytes(StandardCharsets.UTF_8);
        int gcmIvLength = 12;
        String plainText = "plainText";
        try {
            String encoded = encrypt(plainText, key, gcmIvLength, initializationVector);
            String decoded = decrypt(encoded, key, gcmIvLength, initializationVector);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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
            log.error(e.getMessage() + ": " + e.getStackTrace()[0].toString());
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
            log.error(e.getMessage() + ": " + e.getStackTrace()[0].toString());
        }

        return new String(decryptedText);
    }

}
