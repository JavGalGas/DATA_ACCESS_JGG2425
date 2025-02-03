package com.jgg2425.da.fa2.finalactivity2.models.dao;

import com.jgg2425.da.fa2.finalactivity2.models.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface ICategoryDAO extends CrudRepository<Category, Integer> {
}
