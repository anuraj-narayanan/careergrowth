package com.anuraj.flight.service;


import com.anuraj.flight.vo.Mock;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MockService {

    WebClient webClient = WebClient.create("http://mock:8081");

	//CircuitBreaker circuitBreaker = CircuitBreaker.of("mockServiceBackUp", CircuitBreakerConfig.ofDefaults());

	@Autowired
	private CircuitBreaker circuitBreaker;

	public Mono<Mock> getMockResponse(String mockId){
	  return webClient.get()
	        .uri("/mock/{id}", mockId)
	        .retrieve()
			  .onStatus(HttpStatus::is5xxServerError, clientResponse ->
				Mono.error(new RuntimeException()))
	        .bodyToMono(Mock.class)
			  .transform(CircuitBreakerOperator.of(circuitBreaker))
			  .onErrorResume(ex -> mockServiceFallback(mockId));
	}

	private Mono<? extends Mock> mockServiceFallback(String mockId) {
		return Mono.just(new Mock("Downstream service is not available now, please try again later"));
	}


}
