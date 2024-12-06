package com.secureshop.services.impl;

import com.secureshop.dtos.UserDTO;
import com.secureshop.mappers.UserMapper;
import com.secureshop.models.Role;
import com.secureshop.models.User;
import com.secureshop.repositories.RoleRepository;
import com.secureshop.repositories.UserRepository;
import com.secureshop.services.interfaces.UserService;
import com.secureshop.validation.NotFoundExceptionHndler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl  implements UserService {
    private final UserRepository userRepo;
    private final UserMapper userMapper ;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepo;


    @Override
    public UserDTO register(UserDTO request) {
        if (userRepo.existsByLogin(request.getLogin())) {
            throw new ValidationException("Ce login est déjà utilisé");
        }

        User user = userMapper.userDTOToUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setActive(true);

        Role userRole = roleRepo.findByName(request.getRoles())
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(request.getRoles());
                    return roleRepo.save(newRole);
                });

        user.setRoles(Arrays.asList(userRole));

        User savedUser = userRepo.save(user);
        return userMapper.userToUserDTO(savedUser);
    }

    @Override
    public List<UserDTO> listUsers() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepo.findAll().stream()
                .filter(user -> !user.getLogin().equals(currentUsername))
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateUserRoles(Long userId, List<String> roleNames) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundExceptionHndler("Utilisateur non trouvé"));

        List<Role> roles = roleNames.stream()
                .map(roleName -> roleRepo.findByName(roleName)
                        .orElseGet(() -> {
                            Role newRole = new Role();
                            newRole.setName(roleName);
                            return roleRepo.save(newRole);
                        }))
                .collect(Collectors.toList());

        user.setRoles(roles);
        userRepo.save(user);
        log.info("Rôles mis à jour pour l'utilisateur {}: {}", userId, roleNames);
    }


    @Override
    public UserDTO authenticate(String login, String password) {
        log.info("Attempting authentication for user: {}", login);
        User user = userRepo.findByLogin(login)
                .orElseThrow(() -> new ValidationException("Login ou mot de passe incorrect"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ValidationException("Login ou mot de passe incorrect");
        }

        return userMapper.userToUserDTO(user);
    }
}
