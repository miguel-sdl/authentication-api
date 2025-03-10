package com.example.authentication_api.service;

import com.example.authentication_api.exception.UserAlreadyExistsException;
import com.example.authentication_api.model.dto.LoginDTO;
import com.example.authentication_api.model.dto.RegisterDTO;
import com.example.authentication_api.model.entity.User;
import com.example.authentication_api.model.mapper.UserMapper;
import com.example.authentication_api.repository.UserRepository;
import com.example.authentication_api.security.TokenService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Log4j2
@Service
public class AuthService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    TokenService tokenService;
    @Autowired
    AuthenticationManager authenticationManager;

    public void register(RegisterDTO dto) {
        User user = UserMapper.INSTANCE.toUser(dto);

        if (Objects.nonNull(userRepository.findByEmail(user.getEmail()))) {
            throw new UserAlreadyExistsException("Nao e possivel registrar um usuario ja existente");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("salvando usuario no db");
        userRepository.save(user);
    }

    public String login(LoginDTO dto) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());

        Authentication authentication = authenticationManager.authenticate(usernamePassword);

        return tokenService.generateToken((User) authentication.getPrincipal());
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
