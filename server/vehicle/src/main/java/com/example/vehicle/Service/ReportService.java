package com.example.vehicle.Service;

import com.example.vehicle.Entities.Report;
import com.example.vehicle.Repository.CarSalesCount;
import com.example.vehicle.Repository.CustomerSalesCount;
import com.example.vehicle.Repository.ReportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    private final ReportRepo reportRepo;
    @Autowired
    public ReportService(ReportRepo reportRepo) {
        this.reportRepo = reportRepo;
    }

    public List<Report> getAllReport(){
        return reportRepo.findAll();
    }

    public Report getReportById(long id){
        return reportRepo.findById(id).orElse(null);
    }

    public String deleteReport(long reportID){
        reportRepo.deleteById(reportID);
        return "Delete success: " + reportID;
    }

    public List<Report> sortByCustomerNameASC(){
        return reportRepo.sortByCustomerNameASC();
    }

    public List<Report> sortByCustomerNameDESC(){return reportRepo.sortByCustomerNameDESC();}

    public List<Report> sortByCarPriceASC(){
        return reportRepo.sortByCarPriceASC();
    }

    public List<Report> sortByCarPriceDESC(){return reportRepo.sortByCarPriceDESC();}

    public List<CarSalesCount> RecentSales(){return reportRepo.RecentSales();}

    public List<CustomerSalesCount> CustomerSalesCount(){return reportRepo.CustomerSalesCount();}

    public List<Report> sortByNewest(){return reportRepo.sortByNewest();}
    public List<Report> sortByOldest(){return reportRepo.sortByOldest();}
}
