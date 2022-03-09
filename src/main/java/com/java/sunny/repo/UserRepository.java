package com.java.sunny.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.sunny.model.MyUser;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Long> {
	Optional<MyUser> findByUsername(String username);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
}