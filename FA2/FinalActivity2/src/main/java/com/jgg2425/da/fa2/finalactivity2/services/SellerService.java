package com.jgg2425.da.fa2.finalactivity2.services;

import com.jgg2425.da.fa2.finalactivity2.models.dao.ISellerDAO;
import com.jgg2425.da.fa2.finalactivity2.models.dto.SellerDTO;
import com.jgg2425.da.fa2.finalactivity2.models.entities.Seller;
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
public class SellerService {
    @Autowired
    private ISellerDAO sellerDAO;

    @Autowired
    private UtilsService utilsService;

    public ResponseEntity<Seller> findSellerById(@NotNull @Min(0) @PathVariable(value = "id") int id) {
        Optional<Seller> seller = sellerDAO.findById(id);
        return seller.isPresent() ? ResponseEntity.ok(seller.get()) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> updateSeller(
            @Valid @RequestBody SellerDTO seller,
            @PathVariable(value = "id") @NotNull @Min(0) int id,
            @PathVariable(value = "confirm") String passwdCheck) {
        Optional<Seller> optionalSeller = sellerDAO.findById(id);
        if (optionalSeller.isPresent()) {
            if (seller.getName() != null) {
                optionalSeller.get().setName(seller.getName());
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
            if (seller.getUrl() != null) {
                optionalSeller.get().setUrl(seller.getUrl());
            }
            if (seller.getPlainPassword() != null &&
                !seller.getPlainPassword().isEmpty() &&
                seller.getPlainPassword().equals(passwdCheck)) {
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
