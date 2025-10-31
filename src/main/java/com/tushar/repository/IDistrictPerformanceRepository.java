package com.tushar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tushar.entity.DistrictPerformance;

public interface IDistrictPerformanceRepository extends JpaRepository<DistrictPerformance, Long> {

    List<DistrictPerformance> findByDistrictNameIgnoreCase(String districtName);

    List<DistrictPerformance> findByDistrictNameIgnoreCaseAndFinYear(String districtName, String finYear);

    @Query("SELECT DISTINCT d.districtName FROM DistrictPerformance d ORDER BY d.districtName")
    List<String> findDistinctDistricts();

    @Query("SELECT DISTINCT d.finYear FROM DistrictPerformance d ORDER BY d.finYear DESC")
    List<String> findDistinctYears();

    // Top 10 performing districts by total expenditure for a finYear (derived query)
    List<DistrictPerformance> findTop10ByFinYearOrderByTotalExpDesc(String finYear);

    // Average wage rate across months for a district (trend)
    @Query("SELECT d.month, AVG(d.averageWageRatePerDayPerPerson) FROM DistrictPerformance d WHERE d.districtName = :districtName GROUP BY d.month ORDER BY d.month")
    List<Object[]> findAverageWageTrendByDistrict(@Param("districtName") String districtName);

    // Category B work average percentage for each district in a financial year
    @Query("SELECT d.districtName, AVG(d.percentOfCategoryBWorks) FROM DistrictPerformance d WHERE d.finYear = :finYear GROUP BY d.districtName ORDER BY AVG(d.percentOfCategoryBWorks) DESC")
    List<Object[]> findCategoryBWorkComparison(@Param("finYear") String finYear);

    // Overall summary of a district
    @Query("""
            SELECT 
                AVG(d.averageWageRatePerDayPerPerson), 
                SUM(d.totalExp), 
                AVG(d.percentagePaymentsGenerated), 
                AVG(d.percentOfCategoryBWorks)
            FROM DistrictPerformance d 
            WHERE d.districtName = :districtName
        """)
    Object[] findDistrictSummary(@Param("districtName") String districtName);
}
