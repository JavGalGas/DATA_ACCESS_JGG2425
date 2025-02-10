package com.jgg2425.da.fa2.finalactivity2.models.dao;

import com.jgg2425.da.fa2.finalactivity2.models.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductDAO extends CrudRepository<Product, Integer> {
    @Query(value = "SELECT * FROM get_prod_nin_seller_products(:sellerId)", nativeQuery = true)
    List<Product> findProductsNotInSellerProducts(@Param("sellerId") int sellerId);
    @Query(value = "SELECT * FROM get_prod_nin_seller_products(:sellerId, :categoryId)", nativeQuery = true)
    List<Product> findProductsNotInSellerProducts(@Param("sellerId") int sellerId, @Param("categoryId") int categoryId);
}
