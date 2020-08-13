package ru.otus.project.masterPass.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.project.masterPass.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
