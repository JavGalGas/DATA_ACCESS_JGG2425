package com.jgg2425.da.fa2.finalactivity2.services;

import com.jgg2425.da.fa2.finalactivity2.models.dao.IProductDAO;
import com.jgg2425.da.fa2.finalactivity2.models.dao.ISellerDAO;
import com.jgg2425.da.fa2.finalactivity2.models.dao.ISellerProductDAO;
import com.jgg2425.da.fa2.finalactivity2.models.dto.SellerProductDTO;
import com.jgg2425.da.fa2.finalactivity2.models.entities.Product;
import com.jgg2425.da.fa2.finalactivity2.models.entities.Seller;
import com.jgg2425.da.fa2.finalactivity2.models.entities.SellerProduct;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class SellerProdService {

    @Autowired
    private ISellerProductDAO sellerProdDAO;
    @Autowired
    private ISellerDAO sellerDAO;
    @Autowired
    private IProductDAO productDAO;

    public ResponseEntity<?> saveSellerProduct(@Valid @RequestBody SellerProductDTO sellerProduct) {
        Optional<Seller> seller = sellerDAO.findById(sellerProduct.getSellerId());
        Optional<Product> product = productDAO.findById(sellerProduct.getProductId());

        if (seller.isEmpty() && product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Associated seller and product not found");
        } else if (seller.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Associated seller not found");
        } else if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Associated product not found");
        }

        if (sellerProdDAO.existsBySellerAndProduct(seller.get(), product.get())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Seller product already exists.");
        }

        SellerProduct newSellerProduct = new SellerProduct();
        newSellerProduct.setSeller(seller.get());
        newSellerProduct.setProduct(product.get());
        newSellerProduct.setPrice(sellerProduct.getPrice());
        newSellerProduct.setStock(sellerProduct.getStock());
        sellerProdDAO.save(newSellerProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body("Seller product saved successfully.");
    }

    public ResponseEntity<?> updateSellerProduct(@Valid @RequestBody SellerProductDTO sellerProduct) {
        Optional<Seller> seller = sellerDAO.findById(sellerProduct.getSellerId());
        Optional<Product> product = productDAO.findById(sellerProduct.getProductId());

        if (seller.isEmpty() && product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Associated seller and product not found");
        } else if (seller.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Associated seller not found");
        } else if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Associated product not found");
        }

        if (!sellerProdDAO.existsBySellerAndProduct(seller.get(), product.get())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Seller product does not exist.");
        }

        Optional<SellerProduct> updatedSellerProduct = sellerProdDAO.findById(sellerProduct.getSellerId());
        if (!updatedSellerProduct.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Associated seller and product not found");
        } else if (updatedSellerProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Associated seller not found");
        }
        if (checkOfferDates(sellerProduct.getOfferStartDate(), sellerProduct.getOfferEndDate(), seller.get().getId())) {
            updatedSellerProduct.get().setOfferStartDate(sellerProduct.getOfferStartDate());
            updatedSellerProduct.get().setOfferEndDate(sellerProduct.getOfferEndDate());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Seller product updated successfully.");

    }

    private boolean checkOfferDates(LocalDate offerStartDate, LocalDate offerEndDate, Integer sellerId) {
        if (offerStartDate == null || offerEndDate == null) {
            return false; // Invalid dates if null
        }

        // Check that start date is before end date
        if (offerStartDate.isAfter(offerEndDate) || offerStartDate.isEqual(offerEndDate)) {
            return false;
        }

        // Check that the number of days between dates is between 1 and 30
        long daysBetween = ChronoUnit.DAYS.between(offerStartDate, offerEndDate);
        if (daysBetween < 1 || daysBetween > 30) {
            return false;
        }

        //Check the number of offers between the dates
        Integer numberOfOffers = sellerProdDAO.findNumberOfOffersBetween(sellerId, offerStartDate, offerEndDate);
        Seller selectedSeller = sellerDAO.findById(sellerId).orElse(null);
        if (selectedSeller == null) {
            return false;
        }
        return (!selectedSeller.isPro() || numberOfOffers < 3) && (selectedSeller.isPro() || numberOfOffers < 1);
    }
}