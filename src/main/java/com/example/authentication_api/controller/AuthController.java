package com.example.authentication_api.controller;

import com.example.authentication_api.model.dto.LoginDTO;
import com.example.authentication_api.model.dto.LoginResponseDTO;
import com.example.authentication_api.model.dto.RegisterDTO;
import com.example.authentication_api.model.entity.User;
import com.example.authentication_api.service.AuthService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Quando o usuario e cadastrado."),
            @ApiResponse(responseCode = "400", description = "Quando o usuario ja existe ou a validacao de campos falha.")
    }
    )
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO dto) {
        authService.register(dto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quando retorna o token JWT."),
            @ApiResponse(responseCode = "400", description = "Quando a validacao de campos falha."),
            @ApiResponse(responseCode = "403", description = "Quando usuario e/ou senha estao incorretos.")

    })
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO dto) {
        String token = authService.login(dto);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quando retorna todos usuarios"),
            @ApiResponse(responseCode = "401", description = "Quando nao esta autenticado")
    })
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(authService.findAll());
    }

}
