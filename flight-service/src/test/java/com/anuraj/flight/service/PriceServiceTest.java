package com.anuraj.flight.service;

import static org.junit.jupiter.api.Assertions.*;

import com.anuraj.flight.model.Price;
import com.anuraj.flight.model.PricingEngine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Collections;

class PriceServiceTest {

    private PriceService priceService;
    private CacheManager cacheManager;

    @BeforeEach
    void setUp() {
        cacheManager = new ConcurrentMapCacheManager("priceEngineCache");
        priceService = new PriceService(new PricingEngine(), cacheManager);
    }

    @AfterEach
    void tearDown() {
        priceService = null;
        cacheManager = null;
    }

    @Test
    void testGetPrice() {
        // Test case when the price is already cached
        String flightNumber = "0001";
        String date = "24-Mar-2023";
        Price cachedPrice = new Price(new BigDecimal(100),new BigDecimal(10), new BigDecimal(110));
        String key = priceService.getPriceKey(flightNumber, date);
        assertEquals(cachedPrice, priceService.getPrice(flightNumber, date));

        // Test case when the price is not cached
        flightNumber = "0001";
        date = "24-Mar-2023";
        Price calculatedPrice = new Price(new BigDecimal(100),new BigDecimal(10), new BigDecimal(110));
        assertEquals(calculatedPrice, priceService.getPrice(flightNumber, date));
    }

    @Test
    void testPopulatePrice() {
        String flightNumber = "0001";
        String date = "24-Mar-2024";
        Price price = new Price(new BigDecimal(100),new BigDecimal(10), new BigDecimal(110));
        String key = priceService.getPriceKey(flightNumber, date);
        priceService.populatePrice(flightNumber, date, price);
        assertEquals(price, cacheManager.getCache("priceEngineCache").get(key).get());
    }

    @Test
    void testGetPriceKey() {
        String flightNumber = "0001";
        String date = "24-Mar-2023";
        assertEquals("0001" + date, priceService.getPriceKey(flightNumber, date));
    }
}
