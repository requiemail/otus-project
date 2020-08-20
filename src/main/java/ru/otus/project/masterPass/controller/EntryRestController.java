package ru.otus.project.masterPass.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
        return entryService.save(encryptDecryptService.encryptEntry(entry));
    }

    @GetMapping("/all")
    public List<Entry> getAllDecrypted(){
        return entryService.getAll().stream()
                .map(encryptDecryptService::decryptEntry)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public Entry encryptAndUpdate(@PathVariable("id") Long id, @RequestBody Entry entry) {
        return entryService.save(encryptDecryptService.encryptEntry(entry));
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable("id") Long id) {
        entryService.deleteById(id);
        return id;
    }

}
