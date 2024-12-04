package com.secureshop.services.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.secureshop.dtos.ProduitDTO;

public interface ProduitService {
    Page<ProduitDTO> listProduits(Pageable pageable);
    ProduitDTO findProduitById(Long id);
    Page<ProduitDTO> searchProduitsByDesignation(String designation, Pageable pageable);
    ProduitDTO addProduit(ProduitDTO produitDTO);
    ProduitDTO updateProduit(Long id, ProduitDTO produitDTO);
    void deleteProduit(Long id);
    List<ProduitDTO> getProduitsByCategorie(Long categorieId);
    Page<ProduitDTO> filterProduitsByCategorie(Long categorieId, Pageable pageable);

}
