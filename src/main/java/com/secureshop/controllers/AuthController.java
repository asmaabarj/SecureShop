package com.secureshop.controllers;

import com.secureshop.dtos.UserDTO;
import com.secureshop.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserDTO request, HttpServletRequest session) {
        Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
        if (existingAuth != null && existingAuth.isAuthenticated() &&
                !(existingAuth instanceof AnonymousAuthenticationToken)) {
            throw new ValidationException("Vous êtes déjà connecté. Veuillez d'abord vous déconnecter.");
        }
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            UserDTO response = userService.authenticate(request.getLogin(), request.getPassword());
            log.info("Login successful for user: {}", request.getLogin());
            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            log.error("Login failed for user: {}", request.getLogin());
            throw new ValidationException("Identifiants invalides");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserDTO request) {
        Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
        if (existingAuth != null && existingAuth.isAuthenticated() &&
                !(existingAuth instanceof AnonymousAuthenticationToken)) {
            throw new ValidationException("Vous êtes déjà connecté. Veuillez d'abord vous déconnecter.");
        }
        log.info("Register  user !! Succes");
        return ResponseEntity.ok(userService.register(request));
    }


    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout() {
        SecurityContextHolder.clearContext();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Déconnexion réussie");
        return ResponseEntity.ok(response);
    }
}



