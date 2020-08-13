package ru.otus.project.masterPass.service;


import ru.otus.project.masterPass.domain.Entry;

public interface EncryptDecryptService {

    Entry encrypt(Entry plainEntry);
    Entry decrypt(Entry encryptedEntry);

}
