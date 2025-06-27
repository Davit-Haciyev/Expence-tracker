package com.exspensetracker.backend.controller;

import com.exspensetracker.backend.model.dto.UserLogin;
import com.exspensetracker.backend.model.dto.UserRegister;
import com.exspensetracker.backend.model.entity.User;
import com.exspensetracker.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegister request) {
        User user = userService.register(request);
        return ResponseEntity.ok().body(new ApiResponse<>(true, user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLogin request) {
        User user = userService.login(request);
        return ResponseEntity.ok().body(new ApiResponse<>(true, user));
    }
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }

    @Getter
    @AllArgsConstructor
    static class ApiResponse<T> {
        private boolean success;
        private T data;
    }
}
