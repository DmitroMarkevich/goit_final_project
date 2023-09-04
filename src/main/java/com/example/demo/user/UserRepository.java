package com.example.demo.user;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUsername(@Length(min = 5, max = 50) String username);

    Optional<UserEntity> findByEmail(@Length(min = 5, max = 50) String email);

    boolean existsByEmailAndIdNot(@Length(min = 5, max = 50) String email, UUID id);
}