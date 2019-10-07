package com.globant.fligth.api.exercise.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.globant.fligth.api.exercise.constants.FlightApiConstants;
import com.globant.fligth.api.exercise.model.City;
import com.globant.fligth.api.exercise.model.Flight;
import com.globant.fligth.api.exercise.utils.DateUtils;

@Component
public class FlightService {

	public List<Flight> getFligths(String departureDate, String airlineCode) {

		Map<String, String> citiesMap = getCitiesFromService();

		List<Flight> flights = getFligthsFromService();

		LocalDate date = DateUtils.getDateFromString(departureDate);

		// Apply filters if any as the provided flight api doesn't support filters
		flights = flights.parallelStream().map(flight -> {
			// Resolve the city with city code.
			String cityCode = flight.getDepartureCity();
			flight.setDepartureCity(citiesMap.get(cityCode));
			flight.setDepartureCityCode(cityCode);

			cityCode = flight.getDestinationCity();
			flight.setDestinationCity(citiesMap.get(cityCode));
			flight.setDestinationCityCode(cityCode);
			return flight;
		}).filter(flight -> {

			if (airlineCode != null) {
				if (!airlineCode.equals(flight.getAirlineCode())) {
					return false;
				}
			}

			if (date != null) {
				return DateUtils.isSameDay(date, flight.getDepartureDateTime());
			}
			return true;

		}).collect(Collectors.toList());

		return flights;
	}

	private List<Flight> getFligthsFromService() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Flight>> response = restTemplate.exchange(FlightApiConstants.FLIGHTS_GET_API,
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Flight>>() {
				});
		return response.getBody();
	}

	private Map<String, String> getCitiesFromService() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<City>> response = restTemplate.exchange(FlightApiConstants.CITIES_GET_API, HttpMethod.GET,
				null, new ParameterizedTypeReference<List<City>>() {
				});
		List<City> cities = response.getBody();

		Map<String, String> citiesMap = new HashMap<>();
		cities.forEach(city -> {
			citiesMap.put(city.getCityCode(), city.getCityName());
		});

		return citiesMap;
	}

}
