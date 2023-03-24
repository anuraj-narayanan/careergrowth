package com.anuraj.flight.initializer;

import com.anuraj.flight.model.Flight;
import com.anuraj.flight.model.Price;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.anuraj.flight.repository.FlightRepository;
import com.anuraj.flight.service.PriceService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;

@Component
@Profile("!test")
public class FlightInitializer implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(FlightInitializer.class);

  @Autowired
  private FlightRepository flightRepository;
  @Autowired
  private PriceService priceService;

  @Override
  public void run(String... args) throws Exception {
    initialDataSetup();
  }

  private List<Flight> getFlightData() {

    return Arrays.asList(new Flight(null, "001", "EK", "DXB", "LHR", "23-Mar-2023"),
        new Flight(null, "002", "EK", "LHR", "DXB", "23-Mar-2023"));

  }

  private void populatePriceCache() {
    for (int i = 0; i < 10000; i++) {
      String flightNumber = StringUtils.leftPad(String.valueOf(i), 4, "0");
      int baseFare = 100+i;
      int tax = 10+i;
      int totalFare = baseFare+tax;
      priceService.populatePrice(flightNumber, "23-Mar-2023",
          new Price(new BigDecimal(baseFare), new BigDecimal(tax), new BigDecimal(totalFare)));

    }

  }

  private void initialDataSetup() {
    populatePriceCache();
    flightRepository.deleteAll().thenMany(Flux.fromIterable(getFlightData()))
        .flatMap(flightRepository::save).thenMany(flightRepository.findAll()).subscribe(flight -> {
          log.info("Flight inserted from commandLineRunner {}", flight);
        });
  }

}
