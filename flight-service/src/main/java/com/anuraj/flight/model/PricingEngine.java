package com.anuraj.flight.model;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
public class PricingEngine {//Mock

    public Price calculatePrice(String flightNumber, String date){

        return new Price(new BigDecimal(100), new BigDecimal(10), new BigDecimal(110));//mock object
    }
}
