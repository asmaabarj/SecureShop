package com.secureshop.models;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La désignation ne peut pas être vide")
    private String designation;

    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être supérieur à 0")
    private Double prix;

    @Min(value = 0, message = "La quantité ne peut pas être négative")
    private Integer quantite;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;
}
