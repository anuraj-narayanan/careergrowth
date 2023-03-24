package com.anuraj.flight.http.handler;


import com.anuraj.flight.model.Flight;
import com.anuraj.flight.model.Price;
import com.anuraj.flight.repository.FlightRepository;
import com.anuraj.flight.service.FlightService;
import com.anuraj.flight.service.PriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class FlightHandler {

    private static final Logger log = LoggerFactory.getLogger(FlightHandler.class);

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightService flightService;

    @Autowired
    private PriceService priceService;


    public Mono<ServerResponse> getAll(ServerRequest serverRequest){
        return ServerResponse.ok()
                .body(flightRepository.findAll(), Flight.class);
    }

    public Mono<ServerResponse> getId(ServerRequest serverRequest){
        return Mono.just(serverRequest.pathVariable("id"))
                .map(Long::valueOf)
                .flatMap(id -> ServerResponse.ok()
                        .body(flightRepository.findById(id), Flight.class));
    }

    public Mono<ServerResponse> getFlight(ServerRequest serverRequest){
        String flightDate = serverRequest.pathVariable("flightdate");
        String origin = serverRequest.pathVariable("origin");
        String destination = serverRequest.pathVariable("destination");
        return ServerResponse.ok()
                .body(flightService.getFlight(flightDate, origin, destination),Flight.class);
    }

    public Mono<ServerResponse> getPrice(ServerRequest serverRequest){
        String flightNumber = serverRequest.pathVariable("flightnumber");
        String flightDate = serverRequest.pathVariable("flightdate");
        Price price = priceService.getPrice(flightNumber, flightDate);
        if (price == null) {
            return ServerResponse.notFound().build();
        }
        return ServerResponse.ok()
                .body(Mono.just(priceService.getPrice(flightNumber, flightDate)),Price.class);
    }

}
