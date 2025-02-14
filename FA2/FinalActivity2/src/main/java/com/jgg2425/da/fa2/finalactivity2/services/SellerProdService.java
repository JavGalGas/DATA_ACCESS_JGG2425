package com.jgg2425.da.fa2.finalactivity2.services;

import com.jgg2425.da.fa2.finalactivity2.models.dao.ISellerDAO;
import com.jgg2425.da.fa2.finalactivity2.models.dao.ISellerProductDAO;
import com.jgg2425.da.fa2.finalactivity2.models.dto.SellerDTO;
import com.jgg2425.da.fa2.finalactivity2.models.dto.SellerProductDTO;
import com.jgg2425.da.fa2.finalactivity2.models.entities.Seller;
import com.jgg2425.da.fa2.finalactivity2.models.entities.SellerProduct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class SellerProdService {

    @Autowired
    private ISellerProductDAO sellerProdDAO;

    public ResponseEntity<?> saveSellerProduct(
            @Valid @RequestBody SellerProductDTO sellerProduct,
            @PathVariable(value = "id") @NotNull @Min(0) int id
    ) {
        Optional<SellerProduct> optionalSellerProduct = sellerProdDAO.findById(id);
        if (optionalSellerProduct.isPresent()) {
            if (sellerProduct.getSeller() != null) {
                optionalSellerProduct.get().setSeller(sellerProduct.get());
            }
            if (seller.getBusinessName() != null) {
                optionalSeller.get().setBusinessName(seller.getBusinessName());
            }
            if (seller.getEmail() != null) {
                optionalSeller.get().setEmail(seller.getEmail());
            }
            if (seller.getPhone() != null) {
                optionalSeller.get().setPhone(seller.getPhone());
            }
            if (seller.getPlainPassword() != null && !seller.getPlainPassword().isEmpty()) {
                optionalSeller.get().setPlainPassword(seller.getPlainPassword());
                optionalSeller.get().setPassword(utilsService.encode(seller.getPlainPassword()));
            }
            sellerDAO.save(optionalSeller.get());
            return ResponseEntity.ok().body("Updated");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
