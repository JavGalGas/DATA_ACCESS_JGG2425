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

    private List<DropDownMenuOptionDTO> getDropDownMenuOptionDTOS(Optional<List<Object[]>> list) {
        if (list.isPresent()) {
            List<DropDownMenuOptionDTO> optionDTOList = new ArrayList<>();
            for (Object[] row : list.get()) {
                String code = (String) row[0];
                String title = (String) row[1];
                optionDTOList.add(new DropDownMenuOptionDTO(code, title));
            }
            return optionDTOList;
        } else {
            return null;
        }
    }

    public Flight findFlightByFlightCode(@NotBlank String flightCode) {
        Optional<Flight> flight = flightDAO.findById(flightCode);
        return flight.isPresent() ? flight.get() : null;
    }

    public List<DropDownMenuOptionDTO> findAllSources() {
        Optional<List<Object[]>> origins = flightDAO.findAllSources();
        return getDropDownMenuOptionDTOS(origins);
    }

    public List<DropDownMenuOptionDTO> findAllDestinationsBySource(
            @NotBlank @Param("source") String source
    ) {
        Optional<List<Object[]>> destinations = flightDAO.findAllDestinationsBySource(source);
        return getDropDownMenuOptionDTOS(destinations);
    }

    public List<DropDownMenuOptionDTO> findAllFlightsBySourceAndDestination(
            @NotBlank @Param("source") String source,
            @NotBlank @Param("destination") String destination
    ) {
        Optional<List<Object[]>> flights = flightDAO.findAllFlightsBySourceAndDestination(source, destination);
        return getDropDownMenuOptionDTOS(flights);
    }

}
