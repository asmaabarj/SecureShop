package com.secureshop.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.secureshop.dtos.ProduitDTO;
import com.secureshop.models.Produit;

@Mapper(componentModel = "spring")
public interface ProduitMapper {
    @Mapping(target = "categorieId", source = "categorie.id")
    ProduitDTO produitToProduitDTO(Produit produit);
    @Mapping(target = "categorie.id", source = "categorieId")
    Produit produitDTOToProduit(ProduitDTO produitDTO);
}
