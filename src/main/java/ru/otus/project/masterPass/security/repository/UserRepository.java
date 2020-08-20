package ru.otus.project.masterPass.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.project.masterPass.security.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
