package ru.otus.project.masterPass.service.impl;

import ru.otus.project.masterPass.domain.Entry;
import ru.otus.project.masterPass.service.EncryptDecryptService;

import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class AesEncryptDecryptService implements EncryptDecryptService {

    private final SecretKey key;
    private final byte[] initializationVector;

    public static final int GCM_IV_LENGTH = 12;
    public static final int GCM_TAG_LENGTH = 16;

    // Master key must be 32 byte long
    public AesEncryptDecryptService(String masterKey) {
        this.key = new SecretKeySpec(masterKey.getBytes(), 0, masterKey.getBytes().length, "AES");
        initializationVector = new byte[GCM_IV_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(initializationVector);
    }

    public Entry encrypt(Entry entry) throws Exception {
        // Get Cipher Instance
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        // Create SecretKeySpec
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");

        // Create GCMParameterSpec
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, initializationVector);

        // Initialize Cipher for ENCRYPT_MODE
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);

        // Perform Encryption
        entry.setPassword(Base64.getEncoder().encodeToString(cipher.doFinal(entry.getPassword().getBytes())));
        entry.setInitializationVector(Base64.getEncoder().encodeToString(initializationVector));

        return entry;
    }

    public Entry decrypt(Entry entry) throws Exception {
        // Get Cipher Instance
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        // Create SecretKeySpec
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");

        // Create GCMParameterSpec
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, entry.getInitializationVector().getBytes());

        // Initialize Cipher for DECRYPT_MODE
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);

        // Perform Decryption
        entry.setPassword(new String(cipher.doFinal(entry.getPassword().getBytes())));

        return entry;
    }

}
