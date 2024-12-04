package com.secureshop.mappers;

import com.secureshop.dtos.CategorieDTO;
import com.secureshop.models.Categorie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategorieMapper {
    CategorieDTO categorieToCategorieDTO(Categorie categorie);
    Categorie categorieDTOToCategorie(CategorieDTO categorieDTO);
}
