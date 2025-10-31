package com.tushar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tushar.entity.DistrictPerformance;
import com.tushar.service.IDistrictPerformanceService;

@RestController
@RequestMapping("/api/performance")
@CrossOrigin(origins = "*")
public class DistrictPerformanceImple implements IDistrictPerformanceController {
	
	@Autowired
	private IDistrictPerformanceService service;

	@Override
	@GetMapping("/all")
	public List<DistrictPerformance> getAll() {
		return service.getAllData();
	}

	@Override
	@GetMapping("/district/{name}")
	public List<DistrictPerformance> getByDistrict(@PathVariable String name) {
		return service.getByDistrict(name);
	}

	@Override
	@GetMapping("/district/{name}/year{year}")
	public List<DistrictPerformance> getByDistrictAndYear(@PathVariable String name, @PathVariable int year) {
		return service.getByDistrictAndYear(name, year);
	}

	@Override
	@PostMapping("/add")
	public DistrictPerformance addData(@RequestBody DistrictPerformance data) {
		// TODO Auto-generated method stub
		return service.saveData(data);
	}

}
