package com.jgg2425.da.unit5.springemployeeexample.models.dao;

import com.jgg2425.da.unit5.springemployeeexample.models.entities.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeEntityDAO extends CrudRepository<EmployeeEntity, Integer> {
}
