package com.jgg.catchup.flights_jgg;
import com.jgg.catchup.flights_jgg.models.dao.IFlightDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = IFlightDAO.class)
class FlightDAOTests {

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;

    @InjectMocks
    private IFlightDAO flightDAO;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    void findAllSources_success() {
        List<Object[]> mockQueryResult = Arrays.asList(
                new Object[]{"MAD", "Madrid"},
                new Object[]{"BCN", "Barcelona"}
        );

        when(entityManager.createNativeQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(mockQueryResult);

        Optional<List<Object[]>> result = flightDAO.findAllSources();

        assertTrue(result.isPresent());
        List<Object[]> actualList = result.get();
        assertEquals(2, actualList.size());
        assertEquals("MAD", actualList.get(0)[0]);
        assertEquals("Madrid", actualList.get(0)[1]);
        assertEquals("BCN", actualList.get(1)[0]);
        assertEquals("Barcelona", actualList.get(1)[1]);
    }

    @Test
    void findAllSources_noResults() {
        when(entityManager.createNativeQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(java.util.Collections.emptyList());

        Optional<List<Object[]>> result = flightDAO.findAllSources();

        assertTrue(result.isPresent());
        assertTrue(result.get().isEmpty());
    }
}
