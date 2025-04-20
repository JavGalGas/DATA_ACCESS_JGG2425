package com.jgg.catchup.flights_jgg.services;

import com.jgg.catchup.flights_jgg.models.dao.*;
import com.jgg.catchup.flights_jgg.models.dto.DropDownMenuOptionDTO;
import com.jgg.catchup.flights_jgg.models.entities.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    @Autowired
    private IFlightDAO flightDAO;

    public Flight findFlightByFlightCode(@NotBlank String flightCode) {
        Optional<Flight> flight = flightDAO.findById(flightCode);
        return flight.isPresent() ? flight.get() : null;
    }

    public List<DropDownMenuOptionDTO> findAllSources() {
        Optional<List<Object[]>> origins = flightDAO.findAllSources();
        if (origins.isPresent()) {
            List<DropDownMenuOptionDTO> sourcesDto = new ArrayList<>();
            for (Object[] row : origins.get()) {
                String code = (String) row[0];
                String title = (String) row[1];
                sourcesDto.add(new DropDownMenuOptionDTO(code, title));
            }
            return sourcesDto;
        } else {
            return null;
        }
    }

    public List<DropDownMenuOptionDTO> findAllDestinationsBySource(
            @NotBlank @Param("source") String source
    ) {
        Optional<List<Object[]>> destinations = flightDAO.findAllDestinationsBySource(source);
        if (destinations.isPresent()) {
            List<DropDownMenuOptionDTO> destinationsDto = new ArrayList<>();
            for (Object[] row : destinations.get()) {
                String code = (String) row[0];
                String title = (String) row[1];
                destinationsDto.add(new DropDownMenuOptionDTO(code, title));
            }
            return destinationsDto;
        } else {
            return null;
        }
    }

    public List<DropDownMenuOptionDTO> findAllFlightsBySourceAndDestination(
            @NotBlank @Param("source") String source,
            @NotBlank @Param("destination") String destination
    ) {
        Optional<List<Object[]>> flights = flightDAO.findAllFlightsBySourceAndDestination(source, destination);
        if (flights.isPresent()) {
            List<DropDownMenuOptionDTO> flightsDto = new ArrayList<>();
            for (Object[] row : flights.get()) {
                String code = (String) row[0];
                String title = (String) row[1];
                flightsDto.add(new DropDownMenuOptionDTO(code, title));
            }
            return flightsDto;
        } else {
            return null;
        }
    }

}
