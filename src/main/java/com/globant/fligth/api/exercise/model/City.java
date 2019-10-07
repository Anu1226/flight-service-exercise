package com.globant.fligth.api.exercise.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class City {

	@JsonProperty("name")
	private String cityName;

	@JsonProperty("iataCode")
	private String cityCode;

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
}
