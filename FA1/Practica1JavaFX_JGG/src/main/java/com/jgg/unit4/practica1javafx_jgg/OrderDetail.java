package com.jgg.unit4.practica1javafx_jgg;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_details_id_gen")
    @SequenceGenerator(name = "order_details_id_gen", sequenceName = "order_details_order_detail_id_seq", allocationSize = 1)
    @Column(name = "order_detail_id", nullable = false)
    private Integer id;

    @Column(name = "order_id")
    private Integer order;

    @Column(name = "seller_product_id")
    private Integer sellerProduct;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "subtotal_price", precision = 10, scale = 2)
    private BigDecimal subtotalPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getSellerProduct() {
        return sellerProduct;
    }

    public void setSellerProduct(Integer sellerProduct) {
        this.sellerProduct = sellerProduct;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotalPrice() {
        return subtotalPrice;
    }

    public void setSubtotalPrice(BigDecimal subtotalPrice) {
        this.subtotalPrice = subtotalPrice;
    }

}