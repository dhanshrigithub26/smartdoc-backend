package com.yari.smartdoc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yari.smartdoc.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}