package com.secureshop.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {


    @NotNull
    @NotBlank(message = "Le login ne peut pas être vide")
    @Size(min = 3, max = 50)
    private String login;
    @NotNull
    @NotBlank(message = "Le password ne peut pas être vide")
    @Size(min = 6, max = 100)
    private String password;
    @NotNull
    @NotBlank(message = "Le role ne peut pas être vide")
    private String roles;
}
