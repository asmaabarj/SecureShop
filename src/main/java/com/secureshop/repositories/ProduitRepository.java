package com.secureshop.repositories;

import com.secureshop.models.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ProduitRepository extends JpaRepository<Produit, Long> {

}
