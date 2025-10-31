package com.tushar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tushar.entity.DistrictPerformance;
import com.tushar.repository.IDistrictPerformanceRepository;

@Service
public class DistrictPerformanceServiceImple implements IDistrictPerformanceService {
	
	@Autowired
	private IDistrictPerformanceRepository repo;

	@Override
	public List<DistrictPerformance> getAllData() {
		return repo.findAll();
	}

	@Override
	public List<DistrictPerformance> getByDistrict(String districtName) {
		return repo.findByDistrictName(districtName);
	}

	@Override
	public List<DistrictPerformance> getByDistrictAndYear(String districtName, int year) {
		return repo.findByDistrictNameAndYear(districtName, year);
	}

	@Override
	public DistrictPerformance saveData(DistrictPerformance data) {
		return repo.save(data);
	}

}
