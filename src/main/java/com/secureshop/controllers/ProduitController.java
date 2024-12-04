package com.secureshop.controllers;

import com.secureshop.dtos.ProduitDTO;
import com.secureshop.services.interfaces.ProduitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/produits")
@RequiredArgsConstructor
public class ProduitController {
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

    @PostMapping
    public ResponseEntity<ProduitDTO> createProduit(@Valid @RequestBody ProduitDTO produitDTO) {
        return ResponseEntity.ok(produitService.addProduit(produitDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProduitDTO> updateProduit(
            @PathVariable Long id,
            @Valid @RequestBody ProduitDTO produitDTO) {
        return ResponseEntity.ok(produitService.updateProduit(id, produitDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        produitService.deleteProduit(id);
        return ResponseEntity.noContent().build();
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
