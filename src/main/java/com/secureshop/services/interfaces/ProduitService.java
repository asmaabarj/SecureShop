package com.secureshop.services.interfaces;

import com.secureshop.dtos.ProduitDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProduitService {
    Page<ProduitDTO> listProduits(Pageable pageable);
    ProduitDTO findProduitById(Long id);
    Page<ProduitDTO> searchProduitsByDesignation(String designation, Pageable pageable);
    Page<ProduitDTO> filterProduitsByCategorie(Long categorieId, Pageable pageable);
    ProduitDTO addProduit(ProduitDTO produitDTO);
    ProduitDTO updateProduit(Long id, ProduitDTO produitDTO);
    void deleteProduit(Long id);
}
