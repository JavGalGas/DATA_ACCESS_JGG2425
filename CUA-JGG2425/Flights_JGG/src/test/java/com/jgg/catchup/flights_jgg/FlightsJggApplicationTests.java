package com.jgg.catchup.flights_jgg;

import com.jgg.catchup.flights_jgg.controllers.FlightController;
import com.jgg.catchup.flights_jgg.models.dao.IFlightDAO;
import com.jgg.catchup.flights_jgg.models.dto.DropDownMenuOptionDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = FlightsJggApplication.class)
class FlightsJggApplicationTests {

    @Mock
    private IFlightDAO flightDAO;

    @InjectMocks
    private FlightController flightController;

    @Test
    void findOrigins() {
        List<Object[]> origins = new ArrayList<>();
        origins.add(objectConstructor("g", "a"));
        origins.add(objectConstructor("g", "a"));
        origins.add(objectConstructor("g", "a"));

        when(flightDAO.findAllSources()).thenReturn(Optional.of(origins));

        List<DropDownMenuOptionDTO> result =  flightController.findAllSources();
    }

    Object[] objectConstructor(String string1, String string2) {
        Object[] object = new Object[2];
        object[0] = string1;
        object[1] = string2;
        return object;
    }

}
