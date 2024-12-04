package com.secureshop.services.interfaces;

import com.secureshop.dtos.CategorieDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategorieService {
    Page<CategorieDTO> listCategories(Pageable pageable);
    CategorieDTO findCategorieById(Long id);
    Page<CategorieDTO> searchCategoriesByName(String name, Pageable pageable);
    CategorieDTO addCategorie(CategorieDTO categorieDTO);
    CategorieDTO updateCategorie(Long id, CategorieDTO categorieDTO);
    void deleteCategorie(Long id);
}
