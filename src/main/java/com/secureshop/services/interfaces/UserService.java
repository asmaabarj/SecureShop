package com.secureshop.services.interfaces;

import java.util.List;

import com.secureshop.dtos.UserDTO;


public interface UserService  {

    UserDTO register(UserDTO request);
    List<UserDTO> listUsers();
    void updateUserRoles(Long userId, List<String> roleNames);
    UserDTO authenticate(String login, String password);
    boolean existsByLogin(String login);
    
}
