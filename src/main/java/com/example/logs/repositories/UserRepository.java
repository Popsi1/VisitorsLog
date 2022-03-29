package com.example.logs.repositories;

import com.example.logs.enums.UserRole;
import com.example.logs.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmailOrUsername(String email, String username);
    List<User> findUsersByRole(UserRole role);
    Optional<User> findUserByUsername(String username);
}
