package com.jgg2425.da.fa2.finalactivity2.models.dao;

import com.jgg2425.da.fa2.finalactivity2.models.entities.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICategoryDAO extends CrudRepository<Category, Integer> {
    @Query(value = "SELECT * FROM categories WHERE category_id IN (SELECT category_id FROM get_non_added_products(:sellerId))", nativeQuery = true)
    List<Category> findAllCategWithProducts(@Param("SellerId") int sellerId);
}
