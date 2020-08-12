package ru.otus.project.masterPass.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.project.masterPass.domain.Entry;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {
}
