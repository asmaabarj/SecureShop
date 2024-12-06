package com.secureshop.controllers.authentification;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
    public ResponseEntity<?> login(@RequestBody UserDTO request, HttpServletRequest session) {
        Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
        if (existingAuth != null && existingAuth.isAuthenticated() &&
                !(existingAuth instanceof AnonymousAuthenticationToken)) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Vous êtes déjà connecté. Veuillez d'abord vous déconnecter.");
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            UsernamePasswordAuthenticationToken authenticationToken = 
                new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            UserDTO response = userService.authenticate(request.getLogin(), request.getPassword());
            
            Map<String, Object> successResponse = new HashMap<>();
            successResponse.put("message", "Connexion réussie!");
            successResponse.put("user", response);
            
            log.info("Login successful for user: {}", request.getLogin());
            return ResponseEntity.ok(successResponse);

        } catch (AuthenticationException e) {
            log.error("Login failed for user: {}", request.getLogin());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Login ou mot de passe incorrect");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
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
            UserDTO registeredUser = userService.register(request);
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



