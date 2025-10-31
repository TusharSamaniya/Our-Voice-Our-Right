package com.tushar.service;

import java.util.List;

import com.tushar.entity.DistrictPerformance;

public interface IDistrictPerformanceService {
	
	List<DistrictPerformance> getAllData();
	List<DistrictPerformance> getByDistrict(String districtName);
	List<DistrictPerformance> getByDistrictAndYear(String districtName, int year);
	DistrictPerformance saveData(DistrictPerformance data);

}
