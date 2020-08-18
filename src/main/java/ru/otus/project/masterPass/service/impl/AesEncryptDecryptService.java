package ru.otus.project.masterPass.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.otus.project.masterPass.domain.Entry;
import ru.otus.project.masterPass.service.EncryptDecryptService;
import ru.otus.project.masterPass.util.AesCryptUtils;
import ru.otus.project.masterPass.util.Base64Utils;

import java.security.SecureRandom;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Slf4j
@Primary
@Service("aesEncryptDecryptService")
public class AesEncryptDecryptService implements EncryptDecryptService {

    private SecretKey key;
    private final byte[] initializationVector;

    public static final int GCM_IV_LENGTH = 12;
    public static final int GCM_TAG_LENGTH = 16;

    public void setKey(String key){
            this.key = new SecretKeySpec(key.getBytes(), 0, key.getBytes().length, "AES");
    }

    public AesEncryptDecryptService() {
        this.initializationVector = new byte[GCM_IV_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(this.initializationVector);
    }

    public Entry encryptEntry(Entry entry) {
        String encryptedPassword = AesCryptUtils.encrypt(
                entry.getPassword(),
                key,
                GCM_TAG_LENGTH,
                initializationVector
        );
        entry.setPassword(encryptedPassword);
        entry.setInitializationVector(Base64Utils.encodeToString(initializationVector));
        return entry;
    }

    public Entry decryptEntry(Entry entry) {
        String decryptedPassword = AesCryptUtils.decrypt(
                entry.getPassword(),
                key,
                GCM_TAG_LENGTH,
                Base64Utils.decodeToByteArray(entry.getInitializationVector()));
        entry.setPassword(decryptedPassword);
        return entry;
    }

}
