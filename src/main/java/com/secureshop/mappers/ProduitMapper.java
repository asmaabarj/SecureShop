package com.secureshop.mappers;

import com.secureshop.dtos.ProduitDTO;
import com.secureshop.models.Produit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProduitMapper {
    ProduitDTO produitToProduitDTO(Produit produit);
    Produit produitDTOToProduit(ProduitDTO produitDTO);
}
