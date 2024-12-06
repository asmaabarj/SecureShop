package com.secureshop.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.secureshop.dtos.UserDTO;
import com.secureshop.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", expression = "java(String.join(\",\", user.getRoles().stream().map(role -> role.getName()).collect(java.util.stream.Collectors.toList())))")
    UserDTO userToUserDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "roles", ignore = true)
    User userDTOToUser(UserDTO userDTO);
}
