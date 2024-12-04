package com.secureshop.repositories;

import com.secureshop.models.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    Optional<Produit> findByDesignation(String designation);
    boolean existsByDesignation(String designation);
}
