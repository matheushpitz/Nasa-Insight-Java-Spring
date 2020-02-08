package com.mars.sol.nasa.model;

public class SolTemperature {
	
	private final float averageTemperature;
	
	public SolTemperature(float avT) {
		this.averageTemperature = avT;
	}
	
	public float getAverageTemperature() {
		return this.averageTemperature;
	}
	
}
