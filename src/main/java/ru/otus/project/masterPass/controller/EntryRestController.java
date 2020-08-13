package ru.otus.project.masterPass.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.project.masterPass.domain.Entry;
import ru.otus.project.masterPass.service.EncryptDecryptService;
import ru.otus.project.masterPass.service.EntryService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/entry")
public class EntryRestController {

    private final EncryptDecryptService encryptDecryptService;
    private final EntryService entryService;

    @PostMapping
    public Entry encryptAndSave(@RequestBody Entry entry){
        return entryService.save(encryptDecryptService.encrypt(entry));
    }

    @GetMapping("/all")
    public List<Entry> getAllDecrypted(){
        return entryService.getAll().stream()
                .map(encryptDecryptService::decrypt)
                .collect(Collectors.toList());
    }

}
