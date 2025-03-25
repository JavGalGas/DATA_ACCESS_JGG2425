package com.jgg.catchup.flights_jgg.models.dao;

import com.jgg.catchup.flights_jgg.models.entities.Flight;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IFlightDAO extends CrudRepository<Flight, String> {

    @Query(value = "SELECT DISTINCT (flights.source) from flights", nativeQuery = true)
    List<String> findAllSources();

    @Query(value = "SELECT DISTINCT (flights.destination) from flights where source = :source", nativeQuery = true)
    List<String> findAllDestinationsBySource(@Param("source") String source);

    @Query(value = "", nativeQuery = true)
}
