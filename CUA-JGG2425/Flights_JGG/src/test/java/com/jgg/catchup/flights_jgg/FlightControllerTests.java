package com.jgg.catchup.flights_jgg;
import com.jgg.catchup.flights_jgg.controllers.FlightController;
import com.jgg.catchup.flights_jgg.models.dto.DropDownMenuOptionDTO;
import com.jgg.catchup.flights_jgg.services.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = FlightController.class)
class FlightControllerTests {

    @Mock
    private FlightService flightService;

    @InjectMocks
    private FlightController flightController;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    void findAllSources_success() {
        List<DropDownMenuOptionDTO> serviceReturn = Arrays.asList(
                new DropDownMenuOptionDTO("LAX", "Los Angeles International"),
                new DropDownMenuOptionDTO("ORD", "Chicago O'Hare")
        );
        when(flightService.findAllSources()).thenReturn(serviceReturn);

        ResponseEntity<List<DropDownMenuOptionDTO>> responseEntity = flightController.findAllSources();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<DropDownMenuOptionDTO> resultBody = responseEntity.getBody();
        assertNotNull(resultBody);
        assertEquals(2, resultBody.size());
        assertEquals("LAX", resultBody.get(0).getCode());
        assertEquals("Los Angeles International", resultBody.get(0).getTitle());
    }

    @Test
    void findAllSources_serviceReturnsEmptyList() {
        when(flightService.findAllSources()).thenReturn(new ArrayList<>());

        ResponseEntity<List<DropDownMenuOptionDTO>> responseEntity = flightController.findAllSources();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<DropDownMenuOptionDTO> resultBody = responseEntity.getBody();
        assertNotNull(resultBody);
        assertTrue(resultBody.isEmpty());
    }

    @Test
    void findAllSources_serviceThrowsException() {
        when(flightService.findAllSources()).thenThrow(new RuntimeException("Database connection failed"));

        try {
            flightController.findAllSources();
        } catch (RuntimeException exception) {
            assertEquals("Database connection failed", exception.getMessage());
        }
    }
}