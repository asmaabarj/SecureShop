package com.secureshop.services;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.secureshop.dtos.CategorieDTO;
import com.secureshop.exceptions.CategorieException;
import com.secureshop.mappers.CategorieMapper;
import com.secureshop.models.Categorie;
import com.secureshop.repositories.CategorieRepository;
import com.secureshop.services.impl.CategorieServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CategorieServiceTest {

    @Mock
    private CategorieRepository categorieRepository;

    @Mock
    private CategorieMapper categorieMapper;

    @InjectMocks
    private CategorieServiceImpl categorieService;

    private Categorie categorie;
    private CategorieDTO categorieDTO;

    @BeforeEach
    void setUp() {
        categorie = new Categorie();
        categorie.setId(1L);
        categorie.setNom("Test Categorie");
        categorie.setDescription("Test Description");

        categorieDTO = new CategorieDTO();
        categorieDTO.setId(1L);
        categorieDTO.setNom("Test Categorie");
        categorieDTO.setDescription("Test Description");
    }

    @Test
    void listCategories_ShouldReturnPageOfCategories() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<Categorie> categoriePage = new PageImpl<>(Arrays.asList(categorie));
        when(categorieRepository.findAll(pageable)).thenReturn(categoriePage);
        when(categorieMapper.categorieToCategorieDTO(any(Categorie.class))).thenReturn(categorieDTO);

        Page<CategorieDTO> result = categorieService.listCategories(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(categorieRepository).findAll(pageable);
    }

    @Test
    void findCategorieById_WhenCategorieExists_ShouldReturnCategorie() {
        when(categorieRepository.findById(1L)).thenReturn(Optional.of(categorie));
        when(categorieMapper.categorieToCategorieDTO(categorie)).thenReturn(categorieDTO);

        CategorieDTO result = categorieService.findCategorieById(1L);

        assertNotNull(result);
        assertEquals(categorieDTO.getId(), result.getId());
        assertEquals(categorieDTO.getNom(), result.getNom());
    }

    @Test
    void findCategorieById_WhenCategorieDoesNotExist_ShouldThrowException() {
        when(categorieRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CategorieException.class, () -> categorieService.findCategorieById(1L));
    }

    @Test
    void addCategorie_WhenNomDoesNotExist_ShouldSaveCategorie() {
        when(categorieRepository.existsByNom(categorieDTO.getNom())).thenReturn(false);
        when(categorieMapper.categorieDTOToCategorie(categorieDTO)).thenReturn(categorie);
        when(categorieRepository.save(any(Categorie.class))).thenReturn(categorie);
        when(categorieMapper.categorieToCategorieDTO(categorie)).thenReturn(categorieDTO);

        CategorieDTO result = categorieService.addCategorie(categorieDTO);

        assertNotNull(result);
        assertEquals(categorieDTO.getNom(), result.getNom());
        verify(categorieRepository).save(any(Categorie.class));
    }
} 