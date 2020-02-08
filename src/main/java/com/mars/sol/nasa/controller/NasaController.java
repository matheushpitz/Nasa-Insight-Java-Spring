package com.mars.sol.nasa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mars.sol.nasa.model.SolTemperature;
import com.mars.sol.nasa.service.NasaService;

@RestController
@RequestMapping("/nasa")
public class NasaController {
	
	private NasaService service;
	
	public NasaController(NasaService service) {
		this.service = service;
	}

	@GetMapping("/temperature")
	public SolTemperature temperature(@RequestParam(value = "sol", defaultValue = "") String sol) {
		return new SolTemperature(this.service.getSolTemperature(sol));
	}
	
}

