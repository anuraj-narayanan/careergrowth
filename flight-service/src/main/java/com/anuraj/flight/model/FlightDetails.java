package com.anuraj.flight.model;

import com.anuraj.flight.vo.Mock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FlightDetails {

  private Flight flight;
  private Mock mockOne;
  private Mock mockTwo;
  private Mock mockThree;
  private Mock mockFour;
  private Mock mockFive;

}
