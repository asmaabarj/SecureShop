package com.secureshop.controllers;

import com.secureshop.dtos.CategorieDTO;
import com.secureshop.dtos.ProduitDTO;
import com.secureshop.services.interfaces.CategorieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategorieController {


}
