package com.exspensetracker.backend.repository;

import com.exspensetracker.backend.model.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(
            @NotBlank(message = "Email can't be empty")
            @Email(message = "This is not correct email format")
            String email);
    Optional<User> findByEmail(
            @NotBlank(message = "Email can't be empty")
            @Email(message = "This is not correct email format")
            String email);
}