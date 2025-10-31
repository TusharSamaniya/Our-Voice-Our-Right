package com.tushar.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "district_performance")
public class DistrictPerformance {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String stateName;
    private String districtName;
    private String month;
    private int year;
    private int householdsWorked;
    private long totalPersondaysGenerated;
    private double womenParticipationPercentage;
    private double averageWage;
    private double expenditureInLakhs;
    private LocalDate dataSourceDate;
}
