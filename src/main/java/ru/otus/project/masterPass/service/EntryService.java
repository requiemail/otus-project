package ru.otus.project.masterPass.service;

import ru.otus.project.masterPass.domain.Entry;

import java.util.List;
import java.util.Optional;

public interface EntryService {

    Entry save(Entry entry);
    List<Entry> getAll();
    Entry getById(Long id);
    void delete(Entry entry);
    
}
