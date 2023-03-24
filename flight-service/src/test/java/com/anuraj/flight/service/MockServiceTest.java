package com.anuraj.flight.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class MockServiceTest {

    @Autowired
    private MockService mockService;

    @Mock
    private WebClient webClient;

    @Mock
    private CircuitBreaker circuitBreaker;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testGetMockResponse() {


    }

    @Test
    void testGetMockResponseWithCircuitBreaker() {



    }
}

