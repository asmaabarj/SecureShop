package com.secureshop.services.impl;

import com.secureshop.dtos.UserDTO;
import com.secureshop.mappers.UserMapper;
import com.secureshop.models.User;
import com.secureshop.repositories.UserRepository;
import com.secureshop.repositories.RoleRepository;
import com.secureshop.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl {


}