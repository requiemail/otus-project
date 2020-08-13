package ru.otus.project.masterPass.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.project.masterPass.domain.Entry;
import ru.otus.project.masterPass.service.EncryptDecryptService;

@Service
public class MockEncryptDecryptService implements EncryptDecryptService {
    
    @Override
    public Entry encrypt(Entry plainEntry) {
        return plainEntry;
    }

    @Override
    public Entry decrypt(Entry encryptedEntry) {
        return encryptedEntry;
    }
}
