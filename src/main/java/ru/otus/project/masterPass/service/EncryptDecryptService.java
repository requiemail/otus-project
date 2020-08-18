package ru.otus.project.masterPass.service;


import ru.otus.project.masterPass.domain.Entry;

public interface EncryptDecryptService {

    Entry encryptEntry(Entry plainEntry);
    Entry decryptEntry(Entry encryptedEntry);

}
