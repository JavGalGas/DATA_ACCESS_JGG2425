package com.jgg2425.da.fa2.finalactivity2.controllers;

import com.jgg2425.da.fa2.finalactivity2.models.dto.SellerDTO;
import com.jgg2425.da.fa2.finalactivity2.models.entities.Seller;
import com.jgg2425.da.fa2.finalactivity2.services.SellerService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Sellers")
public class SellerController {

    @Autowired
    public SellerService sellerService;

    @GetMapping("/{id}")
    public ResponseEntity<Seller> findSellerById(@PathVariable int id) {
        return sellerService.findSellerById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSeller(@RequestBody @Validated SellerDTO seller,
                                          @NotNull @Min(0) @PathVariable(value = "id") int id) {
        return sellerService.updateSeller(seller, id);
    }
}
