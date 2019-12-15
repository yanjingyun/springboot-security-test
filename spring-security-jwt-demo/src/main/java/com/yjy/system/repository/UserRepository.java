package com.yjy.system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.yjy.system.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    
	Optional<User> findByUsername(String username);

    @Transactional
    void deleteByUsername( String username);
}
