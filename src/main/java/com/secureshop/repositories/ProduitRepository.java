package com.secureshop.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.secureshop.models.Produit;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    Optional<Produit> findByDesignation(String designation);
    boolean existsByDesignation(String designation);
    Page<Produit> findByCategorieId(Long categorieId, Pageable pageable);
}
