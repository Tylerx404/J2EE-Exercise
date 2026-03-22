package com.example.demo5.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo5.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByLoginName(String loginName);

    boolean existsByLoginName(String loginName);
}