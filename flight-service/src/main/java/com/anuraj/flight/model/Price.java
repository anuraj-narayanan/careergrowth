package com.anuraj.flight.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Price {

  private BigDecimal baseFare;
  private BigDecimal tax;
  private BigDecimal totalFare;

}
