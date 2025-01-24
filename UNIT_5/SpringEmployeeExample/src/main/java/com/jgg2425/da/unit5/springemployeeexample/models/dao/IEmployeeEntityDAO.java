package com.jgg2425.da.unit5.springemployeeexample.models.dao;

import com.jgg2425.da.unit5.springemployeeexample.models.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmployeeEntityDAO extends CrudRepository<EmployeeEntity, Integer> {
    @Query("SELECT e FROM EmployeeEntity e WHERE LOWER(e.job) IN :jobs AND e.deptno.id = :deptId")
    List<EmployeeEntity> findByJobInAndDeptno(@Param("jobs") List<String> jobs, @Param("deptId") Integer deptId);

    List<EmployeeEntity> findByDeptno_Id(Integer depno);
}
