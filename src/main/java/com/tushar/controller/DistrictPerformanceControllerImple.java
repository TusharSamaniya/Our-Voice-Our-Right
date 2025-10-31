package com.tushar.controller;

import java.util.List;
import java.util.Map;

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
public class DistrictPerformanceControllerImple implements IDistrictPerformanceController {
	
	@Autowired
	private IDistrictPerformanceService service;
	
	@GetMapping("/all")
    public List<DistrictPerformance> getAllRecords() {
        return service.getAllRecords();
    }

    @GetMapping("/names")
    public List<String> getAllDistricts() {
        return service.getAllDistricts();
    }

    @GetMapping("/years")
    public List<String> getAllYears() {
        return service.getAllYears();
    }

    @GetMapping("/{districtName}")
    public List<DistrictPerformance> getByDistrict(@PathVariable String districtName) {
        return service.getByDistrict(districtName);
    }

    @GetMapping("/{districtName}/{finYear}")
    public List<DistrictPerformance> getByDistrictAndYear(
            @PathVariable String districtName,
            @PathVariable String finYear) {
        return service.getByDistrictAndYear(districtName, finYear);
    }

    @GetMapping("/top/{finYear}")
    public List<DistrictPerformance> getTopDistrictsByExpenditure(@PathVariable String finYear) {
        return service.getTopDistrictsByExpenditure(finYear);
    }

    @GetMapping("/trend/wage/{districtName}")
    public List<Map<String, Object>> getWageTrend(@PathVariable String districtName) {
        return service.getWageTrend(districtName);
    }

    @GetMapping("/compare/categoryB/{finYear}")
    public List<Map<String, Object>> getCategoryBWorkComparison(@PathVariable String finYear) {
        return service.getCategoryBWorkComparison(finYear);
    }

    @GetMapping("/summary/{districtName}")
    public Map<String, Object> getDistrictSummary(@PathVariable String districtName) {
        return service.getDistrictSummary(districtName);
    }
	
	

	

}
