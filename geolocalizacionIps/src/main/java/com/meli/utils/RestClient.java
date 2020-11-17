package com.meli.utils;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.meli.exception.CustomizedException;

public class RestClient {
	
	public static String get(String data, String endpoint) {
	   try{
		   String uri = endpoint + data;
		   RestTemplate restTemplate = new RestTemplate();
		   return restTemplate.getForObject(uri, String.class);
	   }catch (Exception e) {
		   throw new CustomizedException("An error has occurred retriving information of service provider. please retry later");
	   }
	}
	
	public static String get(String data, String accessKey, String endpoint) {
	   try{
		   String uri = endpoint;
		   if(data!=null)
			   uri+=data;
		   uri+="access_key="+accessKey;
		   RestTemplate restTemplate = new RestTemplate();
		   return restTemplate.getForObject(uri, String.class);
	   }catch(RestClientException e ) {
		   throw new CustomizedException("An error has occurred retriving information of service provider. please retry later");
	   }
	}
}
