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
    Optional<List<String>> findAllSources();

    @Query(value = "SELECT DISTINCT (flights.destination) from flights where flights.source = :source", nativeQuery = true)
    Optional<List<String>> findAllDestinationsBySource(@Param("source") String source);

    @Query(value = "SELECT * FROM flights where flights.source = :source and flights.destination = :destination", nativeQuery = true)
    Optional<List<Flight>> findAllFlightsBySourceAndDestination(@Param("source") String source, @Param("destination") String destination);
}
