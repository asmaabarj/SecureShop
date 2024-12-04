package com.secureshop.models;

import lombok.*;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom ne peut pas être vide")
    private String nom;

    @NotBlank(message = "La description est obligatoire")
    private String description;

    @OneToMany(mappedBy = "categorie")
    private List<Produit> produits;

}
