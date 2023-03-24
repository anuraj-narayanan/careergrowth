package com.anuraj.flight.service;

import com.anuraj.flight.model.Price;
import com.anuraj.flight.model.PricingEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache.ValueWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceService {

  private final PricingEngine pricingEngine;
  private final CacheManager cacheManager;

  public Price getPrice(String flightNumber, String date) {
    String key = getPriceKey(flightNumber, date);
    ValueWrapper value = cacheManager.getCache("priceEngineCache").get(key);
    if (value != null) {
      return (Price) value.get();
    }
    return pricingEngine.calculatePrice(flightNumber,date);
  }

  public void populatePrice(String flightNumber, String date, Price price) {
    String key = getPriceKey(flightNumber, date);
    cacheManager.getCache("priceEngineCache").put(key, price);
  }

  public String getPriceKey(String flightNumber, String date) {
    return  StringUtils.leftPad(flightNumber, 4, "0") + date;
  }
}
