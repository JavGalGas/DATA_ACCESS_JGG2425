package com.jgg.catchup.flights_jgg;
import com.jgg.catchup.flights_jgg.models.dao.IFlightDAO;
import com.jgg.catchup.flights_jgg.models.dto.DropDownMenuOptionDTO;
import com.jgg.catchup.flights_jgg.services.FlightService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlightServiceTests {

    @Mock
    private IFlightDAO flightDAO;

    @InjectMocks
    private FlightService flightService;

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

    @Test
    void findAllDestinationsBySource_success() {
        String origin = "CDG";
        List<Object[]> daoReturn = Arrays.asList(
                new Object[]{"JFK", "New York John F. Kennedy"},
                new Object[]{"MMA", "p"}
        );
        when(flightDAO.findAllDestinationsBySource(origin)).thenReturn(Optional.of(daoReturn));

        List<DropDownMenuOptionDTO> result = flightService.findAllDestinationsBySource(origin);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("JFK", result.get(0).getCode());
        assertEquals("New York John F. Kennedy", result.get(0).getTitle());
        assertEquals("MMA", result.get(1).getCode());
        assertEquals("p", result.get(1).getTitle());
    }

    @Test
    void findAllDestinationsBySource_noResultsFromDAO() {
        String origin = "CDG";

        when(flightDAO.findAllDestinationsBySource(origin)).thenReturn(Optional.of(Collections.emptyList()));

        List<DropDownMenuOptionDTO> result = flightService.findAllDestinationsBySource(origin);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findAllDestinationsBySource_emptyOptionalFromDAO() {
        String origin = "CDG";

        when(flightDAO.findAllDestinationsBySource(origin)).thenReturn(Optional.empty());

        List<DropDownMenuOptionDTO> result = flightService.findAllDestinationsBySource(origin);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findAllFlightsBySourceAndDestination_success() {
        String origin = "CDG";
        String destination = "JFK";
        List<Object[]> daoReturn = Arrays.asList(
                new Object[]{"AB0004", "CDG -> JFK : Flight 1"},
                new Object[]{"CD0003", "CDG -> JFK : Flight 2"}
        );
        when(flightDAO.findAllFlightsBySourceAndDestination(origin, destination)).thenReturn(Optional.of(daoReturn));

        List<DropDownMenuOptionDTO> result = flightService.findAllFlightsBySourceAndDestination(origin, destination);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("AB0004", result.get(0).getCode());
        assertEquals("CDG -> JFK : Flight 1", result.get(0).getTitle());
        assertEquals("CD0003", result.get(1).getCode());
        assertEquals("CDG -> JFK : Flight 2", result.get(1).getTitle());
    }

    @Test
    void findAllFlightsBySourceAndDestination_noResultsFromDAO() {
        String origin = "CDG";
        String destination = "JFK";

        when(flightDAO.findAllFlightsBySourceAndDestination(origin, destination)).thenReturn(Optional.of(Collections.emptyList()));

        List<DropDownMenuOptionDTO> result = flightService.findAllFlightsBySourceAndDestination(origin, destination);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findAllFlightsBySourceAndDestination_emptyOptionalFromDAO() {
        String origin = "CDG";
        String destination = "JFK";

        when(flightDAO.findAllFlightsBySourceAndDestination(origin, destination)).thenReturn(Optional.empty());

        List<DropDownMenuOptionDTO> result = flightService.findAllFlightsBySourceAndDestination(origin, destination);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
