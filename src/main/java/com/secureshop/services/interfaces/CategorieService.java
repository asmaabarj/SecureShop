package com.secureshop.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.secureshop.dtos.CategorieDTO;
import com.secureshop.dtos.ProduitDTO;

public interface CategorieService {
    Page<CategorieDTO> listCategories(Pageable pageable);
    CategorieDTO findCategorieById(Long id);
    Page<CategorieDTO> searchCategoriesByNom(String nom, Pageable pageable);
    CategorieDTO addCategorie(CategorieDTO categorieDTO);
    CategorieDTO updateCategorie(Long id, CategorieDTO categorieDTO);
    void deleteCategorie(Long id);
    Page<ProduitDTO> getProduitsForCategorie(Long categorieId, Pageable pageable);
}
