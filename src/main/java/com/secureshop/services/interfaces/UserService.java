package com.secureshop.services.interfaces;

import com.secureshop.dtos.UserDTO;
import com.secureshop.mappers.UserMapper;
import com.secureshop.models.Role;
import com.secureshop.models.User;
import com.secureshop.repositories.RoleRepository;
import com.secureshop.repositories.UserRepository;
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


public interface UserService  {

    UserDTO register(UserDTO request);
    List<UserDTO> listUsers();
    void updateUserRoles(Long userId, List<String> roleNames);
    UserDTO authenticate(String login, String password);
}
