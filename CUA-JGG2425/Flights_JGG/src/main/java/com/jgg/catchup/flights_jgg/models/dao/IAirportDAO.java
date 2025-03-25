package com.jgg.catchup.flights_jgg.models.dao;

import com.jgg.catchup.flights_jgg.models.entities.Airport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAirportDAO extends CrudRepository<Airport, String> {
}
