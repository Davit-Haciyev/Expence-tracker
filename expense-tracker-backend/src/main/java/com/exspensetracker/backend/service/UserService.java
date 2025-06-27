package com.exspensetracker.backend.service;

import com.exspensetracker.backend.model.dto.UserLogin;
import com.exspensetracker.backend.model.dto.UserRegister;
import com.exspensetracker.backend.model.entity.User;
import com.exspensetracker.backend.repository.UserRepository;
import com.exspensetracker.backend.security.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public User register(UserRegister request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);
        savedUser.setToken(jwtUtil.generateToken(String.valueOf(savedUser.getId())));
        return savedUser;
    }

    public User login(UserLogin request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(RuntimeException::new);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Email or password incorrect");
        }

        user.setToken(jwtUtil.generateToken(String.valueOf(user.getId())));
        return user;
    }
    @Transactional
    public User getUserById(String id) {
        return userRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    @Transactional
    public String deleteUserById(Long id) {
        User user = getUserById(String.valueOf(id));
        userRepository.delete(user);
        return "User has been deleted";
    }
}
