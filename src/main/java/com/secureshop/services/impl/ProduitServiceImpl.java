package com.secureshop.services.impl;

import com.secureshop.dtos.ProduitDTO;
import com.secureshop.exceptions.ProduitException;
import com.secureshop.mappers.ProduitMapper;
import com.secureshop.models.Produit;
import com.secureshop.repositories.CategorieRepository;
import com.secureshop.repositories.ProduitRepository;
import com.secureshop.services.interfaces.ProduitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ProduitServiceImpl implements ProduitService {

    private final ProduitRepository produitRepository;
    private final CategorieRepository categorieRepository;
    private final ProduitMapper produitMapper;

    @Override
    public Page<ProduitDTO> listProduits(Pageable pageable) {
        log.info("Listing products with pagination.");
        return produitRepository.findAll(pageable)
                .map(produitMapper::produitToProduitDTO);
    }

    @Override
    public ProduitDTO findProduitById(Long id) {
        log.info("Fetching product with ID: {}", id);
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new ProduitException("Produit introuvable avec l'ID: " + id));
        return produitMapper.produitToProduitDTO(produit);
    }

    @Override
    public Page<ProduitDTO> searchProduitsByDesignation(String designation, Pageable pageable) {
        log.info("Searching products by designation: {}", designation);
        return produitRepository.findAll(pageable)
                .map(produitMapper::produitToProduitDTO);
    }

    @Override
    public Page<ProduitDTO> filterProduitsByCategorie(Long categorieId, Pageable pageable) {
        log.info("Filtering products by category ID: {}", categorieId);
        return produitRepository.findAll(pageable)
                .map(produitMapper::produitToProduitDTO);
    }

    @Override
    public ProduitDTO addProduit(ProduitDTO produitDTO) {
        log.info("Adding a new product: {}", produitDTO.getDesignation());
        if (produitRepository.existsByDesignation(produitDTO.getDesignation())) {
            throw new ProduitException("Un produit avec cette désignation existe déjà.");
        }
        Produit produit = produitMapper.produitDTOToProduit(produitDTO);
        produit.setCategorie(categorieRepository.findById(produitDTO.getCategorieId())
                .orElseThrow(() -> new ProduitException("Catégorie introuvable avec l'ID: " + produitDTO.getCategorieId())));
        produit = produitRepository.save(produit);
        return produitMapper.produitToProduitDTO(produit);
    }

    @Override
    public ProduitDTO updateProduit(Long id, ProduitDTO produitDTO) {
        log.info("Updating product with ID: {}", id);

        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new ProduitException("Produit introuvable avec l'ID: " + id));

        if (produitRepository.existsByDesignation(produitDTO.getDesignation()) && !produit.getDesignation().equals(produitDTO.getDesignation())) {
            throw new ProduitException("Un produit avec cette désignation existe déjà : " + produitDTO.getDesignation());
        }

        produit.setDesignation(produitDTO.getDesignation());
        produit.setPrix(produitDTO.getPrix());
        produit.setQuantite(produitDTO.getQuantite());
        produit.setCategorie(categorieRepository.findById(produitDTO.getCategorieId())
                .orElseThrow(() -> new ProduitException("Catégorie introuvable avec l'ID: " + produitDTO.getCategorieId())));

        produit = produitRepository.save(produit);
        return produitMapper.produitToProduitDTO(produit);
    }


    @Override
    public void deleteProduit(Long id) {
        log.info("Deleting product with ID: {}", id);
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new ProduitException("Produit introuvable avec l'ID: " + id));
        produitRepository.delete(produit);
    }
}
