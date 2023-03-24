package com.anuraj.mock.handler;

import com.anuraj.mock.model.Mock;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import java.util.Random;
import java.time.Duration;

@Component
public class MockHandler {
    public Mono<ServerResponse> getMockOne(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(Mono.just(new Mock("mock1")).delayElement(Duration.ofMillis(getRandomDelay())),Mock.class);
    }
    public Mono<ServerResponse> getMockTwo(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(Mono.just(new Mock("mock2")).delayElement(Duration.ofMillis(getRandomDelay())),Mock.class);
    }
    public Mono<ServerResponse> getMockThree(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(Mono.just(new Mock("mock3")).delayElement(Duration.ofMillis(getRandomDelay())),Mock.class);
    }
    public Mono<ServerResponse> getMockFour(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(Mono.just(new Mock("mock4")).delayElement(Duration.ofMillis(getRandomDelay())),Mock.class);
    }
    public Mono<ServerResponse> mockFive(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(Mono.just(new Mock("mock5")).delayElement(Duration.ofMillis(getRandomDelay())),Mock.class);
    }
    private long getRandomDelay() {
        int lowerBound = 500;
        int upperBound = 800;
        return new Random().nextInt(upperBound-lowerBound)+lowerBound;
    }
}
