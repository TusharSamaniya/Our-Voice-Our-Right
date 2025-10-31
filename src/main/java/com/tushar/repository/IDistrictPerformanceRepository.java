package com.tushar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tushar.entity.DistrictPerformance;

public interface IDistrictPerformanceRepository extends JpaRepository<DistrictPerformance, Long> {

	List<DistrictPerformance> findBystateName(String stateName);
	List<DistrictPerformance> findByDistrictName(String districtName);
	List<DistrictPerformance> findByDistrictNameAndYear(String districtName, int year);
}
