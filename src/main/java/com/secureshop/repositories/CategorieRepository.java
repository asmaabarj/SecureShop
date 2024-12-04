package com.secureshop.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.secureshop.models.Categorie;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    Page<Categorie> findByNomContainingIgnoreCase(String nom, Pageable pageable);
    boolean existsByNom(String nom);
}
