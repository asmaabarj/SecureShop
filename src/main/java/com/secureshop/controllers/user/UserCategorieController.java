package com.secureshop.controllers.user;

import com.secureshop.dtos.CategorieDTO;
import com.secureshop.dtos.ProduitDTO;
import com.secureshop.services.interfaces.CategorieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/user/categories")
@RequiredArgsConstructor
public class UserCategorieController {
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


    @GetMapping("/{id}")
    public ResponseEntity<CategorieDTO> recupererParId(@PathVariable Long id) {
        return ResponseEntity.ok(categorieService.findCategorieById(id));
    }
}
