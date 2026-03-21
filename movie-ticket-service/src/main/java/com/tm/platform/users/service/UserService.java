package com.tm.platform.users.service;

import com.tm.platform.repository.mysql.UserRepository;
import com.tm.platform.security.JwtUtils;
import com.tm.platform.users.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder passwordEncoder;

    public User findUser(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    public User addUser(User user){
        return userRepository.save(user);
    }

    public String login(String email, String password) {
        log.info("Login attempt for user: {}", email);

        // 1. Find user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("Login failed: User {} not found", email);
                    return new RuntimeException("Invalid email or password");
                });

        // 2. Verify password
        // Use passwordEncoder.matches() to compare raw password with hashed DB password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.warn("Login failed: Incorrect password for user {}", email);
            throw new RuntimeException("Invalid email or password");
        }

        // 3. Generate and return JWT Token
        log.info("User {} logged in successfully", email);
        return jwtUtils.generateToken(email);
    }
}
