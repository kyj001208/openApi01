package com.green.app.openapi.weather;

import lombok.Data;

@Data //get,set 둘다 있다는 의미
public class Header {

	private String resultCode;
	private String resultMsg;
	
}
