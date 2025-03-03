package com.example.authentication_api.controller;

import com.example.authentication_api.model.dto.LoginDTO;
import com.example.authentication_api.model.dto.LoginResponseDTO;
import com.example.authentication_api.model.dto.RegisterDTO;
import com.example.authentication_api.model.entity.User;
import com.example.authentication_api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/auth")
@RestController
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO dto) {
        authService.register(dto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO dto) {
        String token = authService.login(dto);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(authService.findAll());
    }

}
