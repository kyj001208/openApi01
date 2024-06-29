package com.green.app.openapi.weather;

import lombok.Data;

@Data
public class WeatherResult {
	
	private Response response; //클래스이름은 중요하지 않지만 변수이름이 동일해야함
}
