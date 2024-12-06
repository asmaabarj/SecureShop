package com.secureshop.controllers.user;


import com.secureshop.dtos.ProduitDTO;
import com.secureshop.services.interfaces.ProduitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/user/produits")
@RequiredArgsConstructor

public class UserProduitController {
    private final ProduitService produitService;

    @GetMapping
    public ResponseEntity<Page<ProduitDTO>> getAllProduits(Pageable pageable) {
        return ResponseEntity.ok(produitService.listProduits(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProduitDTO>> searchProduitsByDesignation(
            @RequestParam String designation,
            Pageable pageable) {
        return ResponseEntity.ok(produitService.searchProduitsByDesignation(designation, pageable));
    }


    @GetMapping("/categorie/{categorieId}")
    public ResponseEntity<List<ProduitDTO>> getProduitsByCategorie(@PathVariable Long categorieId) {
        return ResponseEntity.ok(produitService.getProduitsByCategorie(categorieId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProduitDTO> getProduitById(@PathVariable Long id) {
        return ResponseEntity.ok(produitService.findProduitById(id));
    }

    @GetMapping("/filter/categorie/{categorieId}")
    public ResponseEntity<Page<ProduitDTO>> filterProduitsByCategorie(
            @PathVariable Long categorieId,
            Pageable pageable) {
        return ResponseEntity.ok(produitService.filterProduitsByCategorie(categorieId, pageable));
    }
}
