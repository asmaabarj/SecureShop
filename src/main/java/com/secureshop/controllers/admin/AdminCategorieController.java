package com.secureshop.controllers.admin;


import com.secureshop.dtos.CategorieDTO;
import com.secureshop.dtos.ProduitDTO;
import com.secureshop.services.interfaces.CategorieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor

public class AdminCategorieController {
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
