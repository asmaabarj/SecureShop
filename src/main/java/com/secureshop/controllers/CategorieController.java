package com.secureshop.controllers;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.secureshop.dtos.CategorieDTO;
import com.secureshop.dtos.ProduitDTO;
import com.secureshop.services.interfaces.CategorieService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategorieController {
    private final CategorieService categorieService;

    @GetMapping
    public ResponseEntity<Page<CategorieDTO>> getAllCategories(Pageable pageable) {
        return ResponseEntity.ok(categorieService.listCategories(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CategorieDTO>> searchCategoriesByNom(
            @RequestParam String nom,
            Pageable pageable) {
        return ResponseEntity.ok(categorieService.searchCategoriesByNom(nom, pageable));
    }

    @GetMapping("/{id}/produits")
    public ResponseEntity<Page<ProduitDTO>> getProduitsForCategorie(
            @PathVariable Long id,
            Pageable pageable) {
        return ResponseEntity.ok(categorieService.getProduitsForCategorie(id, pageable));
    }

    @PostMapping
    public ResponseEntity<CategorieDTO> createCategorie(@Valid @RequestBody CategorieDTO categorieDTO) {
        return ResponseEntity.ok(categorieService.addCategorie(categorieDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategorieDTO> updateCategorie(
            @PathVariable Long id,
            @Valid @RequestBody CategorieDTO categorieDTO) {
        return ResponseEntity.ok(categorieService.updateCategorie(id, categorieDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategorie(@PathVariable Long id) {
        categorieService.deleteCategorie(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategorieDTO> recupererParId(@PathVariable Long id) {
        return ResponseEntity.ok(categorieService.findCategorieById(id));
    }
}
