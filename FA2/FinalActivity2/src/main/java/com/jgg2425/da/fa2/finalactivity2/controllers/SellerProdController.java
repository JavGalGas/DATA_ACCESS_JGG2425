package com.jgg2425.da.fa2.finalactivity2.controllers;

import com.jgg2425.da.fa2.finalactivity2.models.dto.SellerProductDTO;
import com.jgg2425.da.fa2.finalactivity2.services.SellerProdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/SellerProducts")
public class SellerProdController {

    @Autowired
    public SellerProdService sellerProdService;

    @PostMapping("")
    public ResponseEntity<?> saveProduct(@RequestBody @Validated SellerProductDTO sellerProduct) {
        return sellerProdService.saveSellerProduct(sellerProduct);
    }

}
