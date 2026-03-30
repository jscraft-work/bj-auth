package com.bj.auth.config.bootstrap;

import com.bj.auth.entity.User;
import com.bj.auth.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Profile("!prod")
public class UserDataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail("admin@bj.com").isPresent()) {
            return;
        }

        User user = new User(
                "admin@bj.com",
                passwordEncoder.encode("password123!"),
                "BJ Admin",
                LocalDateTime.now()
        );

        userRepository.save(user);
    }
}