package com.example.demo5.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo5.model.Account;
import com.example.demo5.model.Role;
import com.example.demo5.repository.AccountRepository;
import com.example.demo5.repository.RoleRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository,
                           AccountRepository accountRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
            .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));
        Role userRole = roleRepository.findByName("ROLE_USER")
            .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

        if (accountRepository.findByLoginName("admin").isEmpty()) {
            Account admin = new Account();
            admin.setLoginName("admin");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setRoles(Set.of(adminRole));
            accountRepository.save(admin);
        }

        if (accountRepository.findByLoginName("user").isEmpty()) {
            Account user = new Account();
            user.setLoginName("user");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(userRole));
            accountRepository.save(user);
        }
    }
}