package com.jgg2425.da.fa2.finalactivity2.models.dao;

import com.jgg2425.da.fa2.finalactivity2.models.entities.SellerProduct;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface ISellerProductDAO {
    Optional<SellerProduct> findById(@NotNull @Min(0) int id);
}
