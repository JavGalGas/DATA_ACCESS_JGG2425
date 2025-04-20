package com.jgg.catchup.flights_jgg.models.dao;

import com.jgg.catchup.flights_jgg.models.dto.DropDownMenuOptionDTO;
import com.jgg.catchup.flights_jgg.models.entities.Flight;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IFlightDAO extends CrudRepository<Flight, String> {

    @Query(value = "SELECT * FROM get_all_sources()", nativeQuery = true)
    Optional<List<Object[]>> findAllSources();

    @Query(value = "SELECT * FROM get_destinations_by_source(:source)", nativeQuery = true)
    Optional<List<Object[]>> findAllDestinationsBySource(@Param("source") String source);

    @Query(value = "SELECT * FROM get_flights_by_source_and_destination(:source, :destination)", nativeQuery = true)
    Optional<List<Object[]>> findAllFlightsBySourceAndDestination(@Param("source") String source, @Param("destination") String destination);
}
