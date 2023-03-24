package com.anuraj.mock.router;

import com.anuraj.mock.handler.MockHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class MockRouter {

    @Bean
    RouterFunction<ServerResponse> routerFunction(final MockHandler mockHandler){

        return RouterFunctions.route(
                RequestPredicates.GET("mock/one"),mockHandler::getMockOne)
                .andRoute(RequestPredicates.GET("mock/two"),mockHandler::getMockTwo)
                .andRoute(RequestPredicates.GET("mock/three"),mockHandler::getMockThree)
                .andRoute(RequestPredicates.GET("mock/four"),mockHandler::getMockFour)
                .andRoute(RequestPredicates.GET("mock/five"),mockHandler::mockFive);

    }
}
