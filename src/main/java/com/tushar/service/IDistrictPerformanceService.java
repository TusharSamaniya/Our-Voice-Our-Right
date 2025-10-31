package com.tushar.service;

import java.util.List;
import java.util.Map;

import com.tushar.entity.DistrictPerformance;

public interface IDistrictPerformanceService {
	
	public List<DistrictPerformance> getAllRecords();
	public List<DistrictPerformance> getByDistrict(String districtName);
	public List<DistrictPerformance> getByDistrictAndYear(String districtName, String finYear);
    public List<String> getAllDistricts();
	public List<String> getAllYears();
	public List<DistrictPerformance> getTopDistrictsByExpenditure(String finYear);
	public List<Map<String, Object>> getWageTrend(String districtName);
	public List<Map<String, Object>> getCategoryBWorkComparison(String finYear);
	public Map<String, Object> getDistrictSummary(String districtName);

}
