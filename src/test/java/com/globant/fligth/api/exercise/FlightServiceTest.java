package com.globant.fligth.api.exercise;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.globant.fligth.api.exercise.model.Flight;
import com.globant.fligth.api.exercise.service.FlightService;
import com.globant.fligth.api.exercise.utils.DateUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlightServiceTest {

	@Autowired
	FlightService flightService;

	@Test
	public void getFlightsWithAirlineCodeFilterTest() {
		String airlineCode = "UA";
		List<Flight> fligtsList = flightService.getFligths(null, airlineCode);
		assertTrue(fligtsList.stream().allMatch(flight -> airlineCode.equals(flight.getAirlineCode())));
	}

	@Test
	public void getFlightsWithDepartureDateFilterTest() {
		String departureDate = "2019-10-15";
		List<Flight> fligtsList = flightService.getFligths(departureDate, null);
		assertTrue(fligtsList.stream().allMatch(flight -> {
			LocalDate date = DateUtils.getDateFromString(departureDate);
			return DateUtils.isSameDay(date, flight.getDepartureDateTime());
		}));
	}

	@Test
	public void getFlightsWithDepartureDateAndAirlineCodeFiltersTest() {
		String departureDate = "2019-10-15";
		String airlineCode = "UA";
		List<Flight> fligtsList = flightService.getFligths(departureDate, null);
		assertTrue(fligtsList.stream().allMatch(flight -> airlineCode.equals(flight.getAirlineCode())));
		assertTrue(fligtsList.stream().allMatch(flight -> {
			LocalDate date = DateUtils.getDateFromString(departureDate);
			return DateUtils.isSameDay(date, flight.getDepartureDateTime());
		}));
	}

	@Test
	public void getFlightsWithoutFiltersTest() {
		List<Flight> fligtsList = flightService.getFligths(null, null);
		assertNotEquals(0, fligtsList.size());
	}

}
