package com.anuraj.flight.service;

import com.anuraj.flight.model.Flight;
import com.anuraj.flight.model.FlightDetails;
import com.anuraj.flight.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class FlightServiceTest {

    @Autowired
    private FlightService flightService;

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private MockService mockService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        flightService = new FlightService();
        ReflectionTestUtils.setField(flightService, "flightRepository", flightRepository);
        ReflectionTestUtils.setField(flightService, "mockService", mockService);
    }

    @Test
    void getFlight() {
        String flightDate = "24-Mar-2023";
        String origin = "DXB";
        String destination = "LHR";
        Flight flight = new Flight();
        flight.setFlightDate(flightDate);
        flight.setOrigin(origin);
        flight.setDestination(destination);
        flight.setFlightNumber("EK0001");

        when(flightRepository.findByFlightDateAndOriginAndDestination(flightDate, origin, destination)).thenReturn(Mono.just(flight));
        when(mockService.getMockResponse(anyString())).thenReturn(Mono.just(new com.anuraj.flight.vo.Mock("Mock")));


        Mono<FlightDetails> result = flightService.getFlight(flightDate, origin, destination);

        // Assert
        StepVerifier.create(result)
                .assertNext(flightDetails -> {
                    assertEquals(flightDetails.getFlight(), flight);
                })
                .expectComplete()
                .verify();
    }
}