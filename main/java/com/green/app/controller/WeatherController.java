package com.green.app.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.green.app.openapi.weather.Item;
import com.green.app.service.WeatherService;
import com.green.app.utils.geo.LatXLngY;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class WeatherController {
	
	
	private final WeatherService service;
	

	@GetMapping("/myweather")
	public List<Item>  weather(LatXLngY latXLngY) {
		System.out.println(latXLngY);
		return service.getWeatherProcess(latXLngY);
	}
	
	
	

}
