package com.jgg2425.da.fa2.finalactivity2.models.dao;

import com.jgg2425.da.fa2.finalactivity2.models.entities.Product;
import com.jgg2425.da.fa2.finalactivity2.models.entities.Seller;
import com.jgg2425.da.fa2.finalactivity2.models.entities.SellerProduct;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ISellerProductDAO extends CrudRepository<SellerProduct, Integer> {
    boolean existsBySellerAndProduct(Seller seller, Product product);
    Optional<List<SellerProduct>> findBySeller(Seller seller);

    @Query(value = "SELECT COUNT(*) FROM seller_products WHERE seller_id = :sellerId AND offer_start_date < :endDate AND offer_end_date >= :startDate", nativeQuery = true)
    Integer findNumberOfOffersBetween(@Param("sellerId") Integer sellerId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
