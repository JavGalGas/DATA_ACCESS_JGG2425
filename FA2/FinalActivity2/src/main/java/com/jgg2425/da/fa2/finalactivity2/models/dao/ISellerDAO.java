package com.jgg2425.da.fa2.finalactivity2.models.dao;

import com.jgg2425.da.fa2.finalactivity2.models.entities.Seller;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ISellerDAO extends CrudRepository<Seller, Integer> {
    Optional<Seller> findByCif(String cif);
}
