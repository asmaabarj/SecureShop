package com.secureshop.models;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le login ne peut pas être vide")
    private String login;

    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    private String password;

    private Boolean active;

    @ManyToMany
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;
}
