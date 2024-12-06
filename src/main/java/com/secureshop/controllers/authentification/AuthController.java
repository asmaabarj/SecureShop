package com.secureshop.controllers.authentification;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.secureshop.dtos.UserDTO;
import com.secureshop.services.interfaces.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO request) {
        Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
        if (existingAuth != null && existingAuth.isAuthenticated() &&
                !(existingAuth instanceof AnonymousAuthenticationToken)) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Vous êtes déjà connecté. Veuillez d'abord vous déconnecter.");
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            UserDTO authenticatedUser = userService.authenticate(request.getLogin(), request.getPassword());
            
            UsernamePasswordAuthenticationToken authToken = 
                new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authToken);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Connexion réussie!");
            response.put("user", authenticatedUser);
            
            log.info("Login successful for user: {}", request.getLogin());
            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            log.error("Login failed for user: {}", request.getLogin());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Login ou mot de passe incorrect");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO request) {
        Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
        if (existingAuth != null && existingAuth.isAuthenticated() &&
                !(existingAuth instanceof AnonymousAuthenticationToken)) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Vous êtes déjà connecté. Veuillez vous déconnecter avant de créer un nouveau compte.");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            if (userService.existsByLogin(request.getLogin())) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Un utilisateur avec ce login existe déjà.");
                return ResponseEntity.badRequest().body(response);
            }

            UserDTO registeredUser = userService.register(request);
            
            UsernamePasswordAuthenticationToken authToken = 
                new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authToken);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Inscription réussie et connexion automatique effectuée!");
            response.put("user", registeredUser);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout() {
        SecurityContextHolder.clearContext();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Déconnexion réussie");
        return ResponseEntity.ok(response);
    }
}



