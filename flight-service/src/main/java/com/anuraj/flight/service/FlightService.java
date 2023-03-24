package com.anuraj.flight.service;

import com.anuraj.flight.model.Flight;
import com.anuraj.flight.model.FlightDetails;
import com.anuraj.flight.repository.FlightRepository;
import com.anuraj.flight.vo.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private MockService mockService;

    private final Map<String, Flight> flightCache = new ConcurrentHashMap<>();

    //@Cacheable("flightCache")
    public Mono<FlightDetails> getFlight(String flightDate, String origin, String destination) {
        // Call 5 mock services in nonblocking and subscribe to another thread
        Mono<Mock> mockOne = mockService.getMockResponse("one").subscribeOn(Schedulers.boundedElastic());
        Mono<Mock> mockTwo = mockService.getMockResponse("two").subscribeOn(Schedulers.boundedElastic());
        Mono<Mock> mockThree = mockService.getMockResponse("three").subscribeOn(Schedulers.boundedElastic());
        Mono<Mock> mockFour = mockService.getMockResponse("four").subscribeOn(Schedulers.boundedElastic());
        Mono<Mock> mockFive = mockService.getMockResponse("five").subscribeOn(Schedulers.boundedElastic());
        //Get the flight information from Cache if present or else from DB(or calling a downstream)
        Mono<Flight> flight = getCachedFlightDetails(flightDate, origin, destination);

        return Mono.zip(mockOne, mockTwo, mockThree, mockFour, mockFive, flight).map(entry ->
                new FlightDetails(entry.getT6(), entry.getT1(), entry.getT2(), entry.getT3(), entry.getT4(), entry.getT5())
        );

    }

    private Mono<Flight> getCachedFlightDetails(String flightDate, String origin, String destination) {

        String flightKey = flightDate + "-" + origin + "-" + destination;
        //Checking cache
        if (flightCache.containsKey(flightKey)) {
            return Mono.just(flightCache.get(flightKey));
        }
        //Return from DB and populate cache with 24hrs expiry
        return flightRepository.findByFlightDateAndOriginAndDestination(flightDate, origin, destination)
                //.delayElement(Duration.ofMillis(800))
                .doOnNext(flight -> {
                    flightCache.put(flightKey, flight);
                    Executors.newSingleThreadScheduledExecutor().schedule(() -> flightCache.remove(flightKey), 24, TimeUnit.HOURS);
                });
    }


}
