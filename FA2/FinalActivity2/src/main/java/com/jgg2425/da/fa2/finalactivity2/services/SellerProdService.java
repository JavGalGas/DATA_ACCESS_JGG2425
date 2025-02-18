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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SellerProdService {

    @Autowired
    private ISellerProductDAO sellerProdDAO;
    @Autowired
    private ISellerDAO sellerDAO;
    @Autowired
    private IProductDAO productDAO;

    public ResponseEntity<?> saveSellerProduct(
            @Valid @RequestBody SellerProductDTO sellerProduct
    ) {
        Optional<Seller> seller = sellerDAO.findById(sellerProduct.getSellerId());
        Optional<Product> product = productDAO.findById(sellerProduct.getProductId());
        boolean isSellerPresent = seller.isPresent();
        boolean isProductPresent = product.isPresent();
        List<String> errors = new ArrayList<>();
        if (!isSellerPresent) {
            errors.add("seller");
        } else if (!isProductPresent) {
            errors.add("product");
        }

        if (seller.isPresent() && product.isPresent()) {
            if (sellerProdDAO.existsBySellerAndProduct(seller.get(), product.get())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Seller product already exists.");
            } else {
                SellerProduct newSellerProduct = new SellerProduct();
                newSellerProduct.setSeller(seller.get());
                newSellerProduct.setProduct(product.get());
                newSellerProduct.setPrice(sellerProduct.getPrice());
                newSellerProduct.setStock(sellerProduct.getStock());
                sellerProdDAO.save(newSellerProduct);
                return ResponseEntity.status(HttpStatus.CREATED).body("Seller product saved successfully.");
            }
        } else {
            if (errors.size() > 1) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Associated " + errors.getFirst() + " and " + errors.getLast() + " not found");
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Associated " + errors.getFirst() + " not found");
        }
    }
}
