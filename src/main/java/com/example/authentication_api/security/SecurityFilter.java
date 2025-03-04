package com.example.authentication_api.security;

import com.example.authentication_api.exception.MissingTokenException;
import com.example.authentication_api.model.entity.User;
import com.example.authentication_api.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (isNecessaryAuthentication(request)) {
            String token = recoverToken(request);
            String username = tokenService.validateToken(token);
            User user = userRepository.findByEmail(username);

            UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(usernamePassword);
        }


        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null) {
            throw new MissingTokenException("E necessario estar autenticado para acessar " + request.getRequestURI());
        }
        return header.replace("Bearer ", "");
    }

    private boolean isNecessaryAuthentication(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        if (requestURI.contains("swagger") || requestURI.contains("api-docs")) return false;
        return !Arrays.asList(SecurityConfig.ENDPOINTS_AUTHENTICATION_NOT_REQUIRED).contains(requestURI);
    }
}
