package com.green.app.openapi.weather;

import lombok.Data;

@Data
public class Item {
	
	private String baseDate;
	private String baseTime;
	private int nx;
	private int ny;
	private String obsrValue;
	private String category;
	
}
