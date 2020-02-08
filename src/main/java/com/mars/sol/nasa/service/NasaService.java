package com.mars.sol.nasa.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mars.sol.config.AppConfig;

@Service
public class NasaService {

	private RestTemplate rest;
	
	public NasaService(RestTemplateBuilder restBuilder) {
		this.rest = restBuilder.build();
	}
	
	public float getSolTemperature(String solName) {
		
		JSONObject data = this.getInsight();
		JSONArray solKeys = data.getJSONArray("sol_keys");			
		
		// Has a sol with this name
		if(this.hasSol(solName, solKeys))
			return this.getTemperatureFromSolObject(data.getJSONObject(solName));
		
		// There is no sol with this name, so let's get the average.				
		return this.getAverageTemperatureFromAll(data, solKeys);
	}
	
	protected JSONObject getInsight() {		
		return new JSONObject(this.rest.getForObject(AppConfig.NASA_URL, String.class));
	}
	
	protected boolean hasSol(String solName, JSONArray solKeys) {
		for(int i = 0; i < solKeys.length(); i++) {
			if(solKeys.getString(i).equals(solName))
				return true;
		}
		return false;
	}
	
	protected float getTemperatureFromSolObject(JSONObject solObject) {			
		return solObject.getJSONObject("AT").getFloat("av");
	}
	
	protected float getAverageTemperatureFromAll(JSONObject data, JSONArray solKeys) {
		float average = 0.0f;
		
		for(int i = 0; i < solKeys.length(); i++) {
			average += this.getTemperatureFromSolObject(data.getJSONObject(solKeys.getString(i)));
		}
		
		return average / solKeys.length();
	}
	
}
