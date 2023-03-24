package com.anuraj.flight.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table("flight")
public class Flight {
	
	@Id	
	private Long id;	
	private String flightNumber;
	private String carrierCode;
	private String origin;
	private String destination;
	private String flightDate;

}
