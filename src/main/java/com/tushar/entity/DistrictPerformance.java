package com.tushar.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "book1")
public class DistrictPerformance {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fin_year")
    private String finYear;

    private String month;

    @Column(name = "district_name")
    private String districtName;

    @Column(name = "Average_Wage_rate_per_day_per_person")
    private Double averageWageRatePerDayPerPerson;

    @Column(name = "Total_Exp")
    private Double totalExp;

    @Column(name = "Total_Households_Worked")
    private Integer totalHouseholdsWorked;

    @Column(name = "percent_of_Category_B_Works")
    private Integer percentOfCategoryBWorks;

    @Column(name = "percentage_payments_generated")
    private Double percentagePaymentsGenerated;
}
