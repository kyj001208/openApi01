package com.green.app.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.green.app.openapi.weather.Item;
import com.green.app.openapi.weather.WeatherResult;
import com.green.app.service.WeatherService;
import com.green.app.utils.OpenApiUtil;
import com.green.app.utils.geo.LatXLngY;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeatherServiceProcess implements WeatherService {

	private final OpenApiUtil apiUtil;
	
	@Value("${data.go.kr.key}") //properties에서 가져올때 사용 
	private String key;
	
	
	@Override
	public List<Item> getWeatherProcess(LatXLngY latXLngY) {
		
		LocalDateTime today=LocalDateTime.now();
		if(today.getMinute()<40)today=today.minusHours(1);
		
		
		DateTimeFormatter dateFormatter=DateTimeFormatter.ofPattern("yyyyMMdd");
		String formattedDate=today.format(dateFormatter);
		
		
		DateTimeFormatter timeFormatter=DateTimeFormatter.ofPattern("HHmm");
		
		String formattedTime=today.format(timeFormatter);
		System.out.println(">>>>>>>>>>>>>>" +formattedTime);
		
		
		
		
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst"); /*URL*/
       
		try {
        	
        	urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "="+key); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
            urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
            urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(formattedDate, "UTF-8")); /*오늘날짜기준 잡고 넣기*/
            urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(formattedTime, "UTF-8")); /*06시 발표(정시단위) */
            urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode((int)latXLngY.getX()+"", "UTF-8")); /*예보지점의 X 좌표값*/ //좌표값경우 컨트롤러 부터 태움
            urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode((int)latXLngY.getY()+"", "UTF-8")); /*예보지점의 Y 좌표값*/
    		
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
		Map<String, String> requestHeaders=new HashMap<>();
		requestHeaders.put("Content-type", "application/json");
		
		
		String methodType="GET"; 
		String requestBody=null;
		
		String weatherResult=apiUtil.request(urlBuilder.toString(), requestHeaders, methodType, requestBody);
		
		WeatherResult result=null;
		
		
		try {
			result=apiUtil.objectMapper(weatherResult, new TypeReference<WeatherResult>() {});
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		System.out.println(result);
		return result.getResponse().getBody().getItems().getItem()
				.stream()
				.filter(item->item.getCategory().equals("REH")||item.getCategory().equals("T1H"))
				//.filter(item->item.getCategory().equals("T1H"))
				.collect(Collectors.toList());
	}

}
