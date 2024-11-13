package com.jgg.unit4.practica1javafx_jgg;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reviews_id_gen")
    @SequenceGenerator(name = "reviews_id_gen", sequenceName = "reviews_review_id_seq", allocationSize = 1)
    @Column(name = "review_id", nullable = false)
    private Integer id;

    @Column(name = "order_detail_id")
    private Integer orderDetail;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "comment", length = Integer.MAX_VALUE)
    private String comment;

    @ColumnDefault("CURRENT_DATE")
    @Column(name = "review_date")
    private LocalDate reviewDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(Integer orderDetail) {
        this.orderDetail = orderDetail;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

}