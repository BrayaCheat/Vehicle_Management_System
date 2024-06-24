package com.example.vehicle.Repository;

import com.example.vehicle.Entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepo extends JpaRepository<Report, Long> {
    @Query(value = "SELECT * FROM tb_report ORDER BY customer_name ASC", nativeQuery = true)
    List<Report> sortByCustomerNameASC();

    @Query(value = "SELECT * FROM tb_report ORDER BY customer_name DESC", nativeQuery = true)
    List<Report> sortByCustomerNameDESC();

    @Query(value = "SELECT * FROM tb_report ORDER BY car_price ASC", nativeQuery = true)
    List<Report> sortByCarPriceASC();

    @Query(value = "SELECT * FROM tb_report ORDER BY car_price DESC", nativeQuery = true)
    List<Report> sortByCarPriceDESC();

    @Query(value = "SELECT * FROM tb_report ORDER BY created_at DESC", nativeQuery = true)
    List<Report> sortByNewest();

    @Query(value = "SELECT * FROM tb_report ORDER BY created_at ASC", nativeQuery = true)
    List<Report> sortByOldest();

    //AS carName refer to data member in repository of CarSalesCount
    @Query(value = "SELECT car_name AS carName, COUNT(*) AS salesCount FROM tb_report GROUP BY car_name ORDER BY salesCount DESC", nativeQuery = true)
    List<CarSalesCount> RecentSales();

    @Query(value = "SELECT customer_name AS customerName, COUNT(*) AS salesCount FROM tb_report GROUP BY customer_name ORDER BY salesCount DESC", nativeQuery = true)
    List<CustomerSalesCount> CustomerSalesCount();
}
