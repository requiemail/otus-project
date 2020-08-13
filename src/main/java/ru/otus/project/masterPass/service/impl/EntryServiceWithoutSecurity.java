package ru.otus.project.masterPass.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.project.masterPass.domain.Entry;
import ru.otus.project.masterPass.error.NotFoundException;
import ru.otus.project.masterPass.repository.EntryRepository;
import ru.otus.project.masterPass.service.EntryService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EntryServiceWithoutSecurity implements EntryService {

    private final EntryRepository repository;

    @Override
    public Entry save(Entry entry) {
        return repository.save(entry);
    }

    @Override
    public List<Entry> getAll() {
        return repository.findAll();
    }

    public Entry getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Entry with id %d not found", id)));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
