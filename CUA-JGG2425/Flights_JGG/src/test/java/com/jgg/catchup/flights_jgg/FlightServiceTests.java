package com.jgg.catchup.flights_jgg;
import com.jgg.catchup.flights_jgg.models.dao.IFlightDAO;
import com.jgg.catchup.flights_jgg.models.dto.DropDownMenuOptionDTO;
import com.jgg.catchup.flights_jgg.services.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = FlightService.class)
class FlightServiceTests {

    @Mock
    private IFlightDAO flightDAO;

    @InjectMocks
    private FlightService flightService;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    void findAllSources_success() {
        List<Object[]> daoReturn = Arrays.asList(
                new Object[]{"CDG", "Paris Charles de Gaulle"},
                new Object[]{"JFK", "New York John F. Kennedy"}
        );
        when(flightDAO.findAllSources()).thenReturn(Optional.of(daoReturn));

        List<DropDownMenuOptionDTO> result = flightService.findAllSources();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("CDG", result.get(0).getCode());
        assertEquals("Paris Charles de Gaulle", result.get(0).getTitle());
        assertEquals("JFK", result.get(1).getCode());
        assertEquals("New York John F. Kennedy", result.get(1).getTitle());
    }

    @Test
    void findAllSources_noResultsFromDAO() {
        when(flightDAO.findAllSources()).thenReturn(Optional.of(Collections.emptyList()));

        List<DropDownMenuOptionDTO> result = flightService.findAllSources();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findAllSources_emptyOptionalFromDAO() {
        when(flightDAO.findAllSources()).thenReturn(Optional.empty());

        List<DropDownMenuOptionDTO> result = flightService.findAllSources();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
