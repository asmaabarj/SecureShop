package com.secureshop.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.secureshop.models.Produit;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    Page<Produit> findByDesignationContainingIgnoreCase(String designation, Pageable pageable);
    boolean existsByDesignation(String designation);
    List<Produit> findByCategorieId(Long categorieId);
    @Query("SELECT p FROM Produit p WHERE p.categorie.id = :categorieId")
    Page<Produit> findByCategorieId(Long categorieId, Pageable pageable);
}

