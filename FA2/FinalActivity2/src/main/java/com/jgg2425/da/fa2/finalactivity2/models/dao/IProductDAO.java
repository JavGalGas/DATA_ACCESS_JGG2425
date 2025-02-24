package com.jgg2425.da.fa2.finalactivity2.models.dao;

import com.jgg2425.da.fa2.finalactivity2.models.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductDAO extends CrudRepository<Product, Integer> {
    /**
     *
     * @param sellerId
     * @return
     * @<code>
     *     CREATE OR REPLACE FUNCTION get_non_added_products(seller_id_param INTEGER)
     *      RETURNS SETOF products
     *      AS $$
     *      BEGIN
     *          RETURN QUERY
     *          SELECT p.*
     *          FROM products p
     *          WHERE p.product_id NOT IN (
     *              SELECT s.product_id
     *              FROM seller_products s
     *              WHERE s.seller_id = seller_id_param
     *              );
     *      END; $$
     *      LANGUAGE plpgsql;
     * </code>
     */
    @Query(value = "SELECT * FROM get_non_added_products(:sellerId)", nativeQuery = true)
    List<Product> getNonAddedProducts(@Param("sellerId") int sellerId);

    /**
     *
     * @param sellerId
     * @param categoryId
     * @return
     * @<code>
     *     CREATE OR REPLACE FUNCTION get_non_added_products_by_category(seller_id_param INTEGER, category_id_param INTEGER)
     *      RETURNS SETOF products
     *      AS $$
     *      BEGIN
     *          RETURN QUERY
     *          SELECT p.*
     *          FROM products p
     *          WHERE p.product_id NOT IN (
     *              SELECT s.product_id
     *              FROM seller_products s
     *              WHERE s.seller_id = seller_id_param
     *              )
     *          AND p.category_id = category_id_param;
     *      END; $$
     *      LANGUAGE plpgsql;
     * </code>
     */
    @Query(value = "SELECT * FROM get_non_added_products_by_category(:sellerId, :categoryId)", nativeQuery = true)
    List<Product> getNonAddedProductsByCategory(@Param("sellerId") int sellerId, @Param("categoryId") int categoryId);
}
