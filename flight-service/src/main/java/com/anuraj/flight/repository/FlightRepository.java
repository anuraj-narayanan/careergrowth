package com.anuraj.flight.repository;

import com.anuraj.flight.model.Flight;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface FlightRepository extends ReactiveCrudRepository<Flight, Long>{

	@Query("SELECT flightNumber from Flight f where f.origin like :#{#origin==null || #origin.isEmpty() ? '%' : #origin}"
			+ " and f.destination like :#{#destination==null || #destination.isEmpty() ? '%' : #destination}"
			+ " and f.flightDate like :#{#flightDate==null || #flightDate.isEmpty() ? '%' : #flightDate}")
	Mono<String> findFlight(String origin, String destination, String flightDate);

	Mono<Flight> findByFlightDateAndOriginAndDestination(String flightDate, String origin, String destination);

}
