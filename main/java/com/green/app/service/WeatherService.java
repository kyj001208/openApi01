package com.green.app.service;

import java.util.List;

import com.green.app.openapi.weather.Item;
import com.green.app.utils.geo.LatXLngY;

public interface WeatherService {

	

	List<Item>  getWeatherProcess(LatXLngY latXLngY);

}
