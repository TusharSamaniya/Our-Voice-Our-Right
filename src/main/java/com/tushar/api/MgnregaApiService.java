package com.tushar.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tushar.entity.DistrictPerformance;
import com.tushar.service.IDistrictPerformanceService;

@Service
public class MgnregaApiService {
	
	@Autowired
	private IDistrictPerformanceService service;
	
	private static final String API_URL = "https://api.data.gov.in/resource/{resource-id}?api-key={your-api-key}&format=json\r\n";
	
	public void fetchAndSaveData() {
		RestTemplate restTemplate = new RestTemplate();
		Map<String, Object> response = restTemplate.getForObject(API_URL, Map.class);
		
		List<Map<String, Object>> records = (List<Map<String, Object>>) response.get("records");
		
		for(Map<String, Object> record : records) {
			DistrictPerformance dp = new DistrictPerformance();
			dp.setStateName((String) record.get("state_name"));
			dp.setDistrictName((String) record.get("district_name"));
			dp.setMonth((String) record.get("month"));
			dp.setYear(Integer.parseInt((String) record.get("fin_year")));
			dp.setHouseholdsWorked(Integer.parseInt((String) record.get("no_of_households_worked")));
			dp.setTotalPersondaysGenerated((long) record.get("total_persondays_generated"));
			dp.setWomenParticipationPercentage(Double.parseDouble((String) record.get("women_participation")));
			dp.setAverageWage(Double.parseDouble((String) record.get("average_wage_rate")));
			dp.setExpenditureInLakhs(Double.parseDouble((String) record.get("total_expenditure_in_lakhs")));
			dp.setDataSourceDate(java.time.LocalDate.now());
			
			service.saveData(dp);
		}
		System.out.println("Data fetch and saved successfully from API");
	}

}
