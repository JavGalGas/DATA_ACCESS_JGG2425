package com.jgg2425.da.fa2.finalactivity2.models.dao;

import com.jgg2425.da.fa2.finalactivity2.models.entities.Product;
import com.jgg2425.da.fa2.finalactivity2.models.entities.Seller;
import com.jgg2425.da.fa2.finalactivity2.models.entities.SellerProduct;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ISellerProductDAO extends CrudRepository<SellerProduct, Integer> {
    boolean existsBySellerAndProduct(Seller seller, Product product);
}
