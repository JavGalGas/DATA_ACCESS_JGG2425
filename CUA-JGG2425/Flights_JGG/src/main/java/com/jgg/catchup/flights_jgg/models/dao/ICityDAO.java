package com.jgg.catchup.flights_jgg.models.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICityDAO extends CrudRepository<City, Long> {
}
