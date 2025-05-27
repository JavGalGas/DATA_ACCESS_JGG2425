package com.jgg.catchup.flights_jgg;

import com.jgg.catchup.flights_jgg.controllers.FlightController;
import com.jgg.catchup.flights_jgg.models.dao.IFlightDAO;
import com.jgg.catchup.flights_jgg.models.dto.DropDownMenuOptionDTO;
import com.jgg.catchup.flights_jgg.services.FlightService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = FlightsJggApplication.class)
class FlightsJggApplicationTests {

    @Mock
    private IFlightDAO flightDAO;

    @InjectMocks
    private FlightService flightService;

    @Test
    void findOrigins() {
        List<Object[]> origins = new ArrayList<>();
        objectListSetter(origins, "code1", "value1");
        objectListSetter(origins, "code2", "value2");
        objectListSetter(origins, "code3", "value3");

        when(flightDAO.findAllSources()).thenReturn(Optional.of(origins));

        List<DropDownMenuOptionDTO> result = flightService.findAllSources();

        assertEquals(200, result.getStatusCode().value());
        Assertions.assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    void findOriginsNotExists() {
        when(flightDAO.findAllSources()).thenReturn(Optional.empty());

        List<DropDownMenuOptionDTO> result = flightService.findAllSources();
        assertEquals(404, result.getStatusCode().value());
    }

    @Test
    void findDestinationsBySource() {
        String sourceCode = "code1";
        List<DropDownMenuOptionDTO> expectedDtoList = new ArrayList<>();
        expectedDtoList.add(new DropDownMenuOptionDTO("code1", "value1"));
        expectedDtoList.add(new DropDownMenuOptionDTO("code2", "value2"));
        expectedDtoList.add(new DropDownMenuOptionDTO("code3", "value3"));

        when(flightService.findAllDestinationsBySource(sourceCode)).thenReturn(expectedDtoList);

        ResponseEntity<List<DropDownMenuOptionDTO>> result = flightController.findAllDestinationsBySource(sourceCode);

        assertEquals(200, result.getStatusCode().value());
        Assertions.assertNotNull(result.getBody());
        assertEquals(3, result.getBody().size());
    }

    @Test
    void findDestinationsBySourceNotExists() {
        String sourceCode = "code1";

        when(flightService.findAllDestinationsBySource(sourceCode)).thenReturn(Collections.emptyList());

        ResponseEntity<List<DropDownMenuOptionDTO>> result = flightController.findAllDestinationsBySource(sourceCode);

        assertEquals(404, result.getStatusCode().value());
    }

    void objectListSetter(List<Object[]> list, String code, String title) {
        Object[] row = new Object[2];
        row[0] = code;
        row[1] = title;
        list.add(row);
    }

}
