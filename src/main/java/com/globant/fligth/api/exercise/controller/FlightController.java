package com.globant.fligth.api.exercise.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.globant.fligth.api.exercise.model.Flight;
import com.globant.fligth.api.exercise.service.FlightService;

@RestController
@RequestMapping(path = "/api/", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
public class FlightController {

	@Autowired
	FlightService flightService;

	@GetMapping(path = "/flight")
	public ResponseEntity<List<Flight>> getFlights(
			@RequestParam(value = "departureDate", required = false) String departureDateStr,
			@RequestParam(value = "airlineCode", required = false) String airlineCode) {
		List<Flight> flights = flightService.getFligths(departureDateStr, airlineCode);
		return new ResponseEntity<List<Flight>>(flights, HttpStatus.OK);
	}

}
