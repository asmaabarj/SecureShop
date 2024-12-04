package com.secureshop.services.impl;

import com.secureshop.dtos.CategorieDTO;
import com.secureshop.exceptions.CategorieException;
import com.secureshop.mappers.CategorieMapper;
import com.secureshop.models.Categorie;
import com.secureshop.repositories.CategorieRepository;
import com.secureshop.services.interfaces.CategorieService;
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
public class CategorieServiceImpl implements CategorieService {

    private final CategorieRepository categorieRepository;
    private final CategorieMapper categorieMapper;

    @Override
    public Page<CategorieDTO> listCategories(Pageable pageable) {
        log.info("Listing categories with pagination.");
        return categorieRepository.findAll(pageable)
                .map(categorieMapper::categorieToCategorieDTO);
    }

    @Override
    public CategorieDTO findCategorieById(Long id) {
        log.info("Fetching category with ID: {}", id);
        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new CategorieException("Categorie introuvable avec l'ID: " + id));
        return categorieMapper.categorieToCategorieDTO(categorie);
    }

    @Override
    public Page<CategorieDTO> searchCategoriesByName(String name, Pageable pageable) {
        log.info("Searching categories by name: {}", name);
        return categorieRepository.findAll(pageable)
                .map(categorieMapper::categorieToCategorieDTO);
    }

    @Override
    public CategorieDTO addCategorie(CategorieDTO categorieDTO) {
        log.info("Adding a new category: {}", categorieDTO.getNom());
        if (categorieRepository.existsByNom(categorieDTO.getNom())) {
            throw new CategorieException("Une catégorie avec ce nom existe déjà.");
        }
        Categorie categorie = categorieMapper.categorieDTOToCategorie(categorieDTO);
        categorie = categorieRepository.save(categorie);
        return categorieMapper.categorieToCategorieDTO(categorie);
    }

    @Override
    public CategorieDTO updateCategorie(Long id, CategorieDTO categorieDTO) {
        log.info("Updating category with ID: {}", id);

        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new CategorieException("Categorie introuvable avec l'ID: " + id));

        if (categorieRepository.existsByNom(categorieDTO.getNom()) && !categorie.getNom().equals(categorieDTO.getNom())) {
            throw new CategorieException("Une catégorie avec ce nom existe déjà : " + categorieDTO.getNom());
        }

        categorie.setNom(categorieDTO.getNom());
        categorie.setDescription(categorieDTO.getDescription());

        categorie = categorieRepository.save(categorie);
        return categorieMapper.categorieToCategorieDTO(categorie);
    }


    @Override
    public void deleteCategorie(Long id) {
        log.info("Deleting category with ID: {}", id);
        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new CategorieException("Categorie introuvable avec l'ID: " + id));
        categorieRepository.delete(categorie);
    }
}
