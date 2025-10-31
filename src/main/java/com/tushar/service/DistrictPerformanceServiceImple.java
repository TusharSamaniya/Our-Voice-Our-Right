package com.tushar.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tushar.entity.DistrictPerformance;
import com.tushar.repository.IDistrictPerformanceRepository;

@Service
public class DistrictPerformanceServiceImple implements IDistrictPerformanceService {

    @Autowired
    private IDistrictPerformanceRepository repository;

    @Override
    public List<DistrictPerformance> getAllRecords() {
        return repository.findAll();
    }

    @Override
    public List<DistrictPerformance> getByDistrict(String districtName) {
        return repository.findByDistrictNameIgnoreCase(districtName);
    }

    @Override
    public List<DistrictPerformance> getByDistrictAndYear(String districtName, String finYear) {
        return repository.findByDistrictNameIgnoreCaseAndFinYear(districtName, finYear);
    }

    @Override
    public List<String> getAllDistricts() {
        return repository.findDistinctDistricts();
    }

    @Override
    public List<String> getAllYears() {
        return repository.findDistinctYears();
    }

    @Override
    public List<DistrictPerformance> getTopDistrictsByExpenditure(String finYear) {
        return repository.findTop10ByFinYearOrderByTotalExpDesc(finYear);
    }

    @Override
    public List<Map<String, Object>> getWageTrend(String districtName) {
        List<Object[]> results = repository.findAverageWageTrendByDistrict(districtName);
        List<Map<String, Object>> trend = new ArrayList<>();
        if (results != null) {
            for (Object[] row : results) {
                Map<String, Object> map = new HashMap<>();
                map.put("month", row[0]);
                map.put("averageWage", row[1]);
                trend.add(map);
            }
        }
        return trend;
    }

    @Override
    public List<Map<String, Object>> getCategoryBWorkComparison(String finYear) {
        List<Object[]> results = repository.findCategoryBWorkComparison(finYear);
        List<Map<String, Object>> list = new ArrayList<>();
        if (results != null) {
            for (Object[] row : results) {
                Map<String, Object> map = new HashMap<>();
                map.put("district", row[0]);
                map.put("avgCategoryBPercent", row[1]);
                list.add(map);
            }
        }
        return list;
    }

    @Override
    public Map<String, Object> getDistrictSummary(String districtName) {
        Object[] row = repository.findDistrictSummary(districtName);
        Map<String, Object> map = new HashMap<>();
        if (row != null) {
            map.put("avgWageRate", row[0]);
            map.put("totalExpenditure", row[1]);
            map.put("avgPaymentGeneratedPercent", row[2]);
            map.put("avgCategoryBWorks", row[3]);
        } else {
            map.put("avgWageRate", null);
            map.put("totalExpenditure", 0);
            map.put("avgPaymentGeneratedPercent", null);
            map.put("avgCategoryBWorks", null);
        }
        return map;
    }
}
