package com.anuraj.flight.http.router;



import com.anuraj.flight.http.handler.FlightHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class FlightRouter {
	
	@Bean
	RouterFunction<ServerResponse> routerFunction(final FlightHandler flightHandler){
		
		return RouterFunctions.route(				
				RequestPredicates.GET("/flight/all"),flightHandler::getAll)
				.andRoute(RequestPredicates.GET("/flight/{id}"),flightHandler::getId)
				.andRoute(RequestPredicates.GET("/flight/{flightdate}/{origin}/{destination}"),flightHandler::getFlight)
				.andRoute(RequestPredicates.GET("/price/{flightnumber}/{flightdate}"),flightHandler::getPrice);
	}

}
