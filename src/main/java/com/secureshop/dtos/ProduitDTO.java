package com.secureshop.dtos;

import lombok.*;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProduitDTO {
    
    private Long id;

    @NotBlank(message = "La désignation ne peut pas être vide")
    private String designation;

    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être supérieur à 0")
    private Double prix;

    @Min(value = 0, message = "La quantité ne peut pas être négative")
    private Integer quantite;

    @NotNull(message = "L'ID de la categorie est obligatoire")
    private Long categorieId;
}
