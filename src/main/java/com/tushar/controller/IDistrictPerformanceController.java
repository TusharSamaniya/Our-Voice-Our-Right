package com.tushar.controller;

import java.util.List;

import com.tushar.entity.DistrictPerformance;

public interface IDistrictPerformanceController {
	
	List<DistrictPerformance> getAll();
	List<DistrictPerformance> getByDistrict(String name);
	List<DistrictPerformance> getByDistrictAndYear(String name, int year);
	DistrictPerformance addData(DistrictPerformance data);
	String fetchDataFromAPI();
	
}
