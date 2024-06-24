package com.example.vehicle.Controller;

import com.example.vehicle.Entities.ApiResponse.APIResponse;
import com.example.vehicle.Entities.Report;
import com.example.vehicle.Repository.CarSalesCount;
import com.example.vehicle.Repository.CustomerSalesCount;
import com.example.vehicle.Service.ReportService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {


    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<Report>>> getAllReport() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new APIResponse<>(
                                "List All Reports",
                                reportService.getAllReport(),
                                HttpStatus.OK,
                                LocalDateTime.now()
                        )
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<Report>> getReportById(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new APIResponse<>(
                                "Get Report By ID: " + id,
                                reportService.getReportById(id),
                                HttpStatus.OK,
                                LocalDateTime.now()
                        )
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteReport(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new APIResponse<>(
                                "Deleted Report With ID: " + id,
                                reportService.deleteReport(id),
                                HttpStatus.OK,
                                LocalDateTime.now()
                        )
                );
    }

    @GetMapping("/sortByCarPriceASC")
    public ResponseEntity<APIResponse<List<Report>>> sortByCarPriceASC() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new APIResponse<>(
                                "Sort By Vehicle's Price Ascending",
                                reportService.sortByCarPriceASC(),
                                HttpStatus.OK,
                                LocalDateTime.now()
                        )
                );
    }

    @GetMapping("/sortByCarPriceDESC")
    public ResponseEntity<APIResponse<List<Report>>> sortByCarPriceDESC() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new APIResponse<>(
                                "Sort By Vehicle's Price Descending",
                                reportService.sortByCarPriceDESC(),
                                HttpStatus.OK,
                                LocalDateTime.now()
                        )
                );
    }

    @GetMapping("/sortByCustomerNameASC")
    public ResponseEntity<APIResponse<List<Report>>> sortByCustomerNameASC(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new APIResponse<>(
                                "Sort By Customer's Name Ascending",
                                reportService.sortByCustomerNameASC(),
                                HttpStatus.OK,
                                LocalDateTime.now()
                        )
                );
    }

    @GetMapping("/sortByCustomerNameDESC")
    public ResponseEntity<APIResponse<List<Report>>> sortByCustomerNameDESC(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new APIResponse<>(
                                "Sort By Customer's Name Descending",
                                reportService.sortByCustomerNameDESC(),
                                HttpStatus.OK,
                                LocalDateTime.now()
                        )
                );
    }

    @GetMapping("/RecentSales")
    public ResponseEntity<APIResponse<List<CarSalesCount>>> RecentSales(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                    new APIResponse<>(
                            "List All Recent Sales",
                            reportService.RecentSales(),
                            HttpStatus.OK,
                            LocalDateTime.now()
                    )
                );
    }

    @GetMapping("/CustomerSalesCount")
    public ResponseEntity<APIResponse<List<CustomerSalesCount>>> CustomerSalesCount(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new APIResponse<>(
                                "List All Loyal Customers",
                                reportService.CustomerSalesCount(),
                                HttpStatus.OK,
                                LocalDateTime.now()
                        )
                );
    }

    @GetMapping("/sortByNewest")
    public ResponseEntity<APIResponse<List<Report>>> sortByNewest(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new APIResponse<>(
                                "Sort By Newest Date",
                                reportService.sortByNewest(),
                                HttpStatus.OK,
                                LocalDateTime.now()
                        )
                );
    }

    @GetMapping("/sortByOldest")
    public ResponseEntity<APIResponse<List<Report>>> sortByOldest(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new APIResponse<>(
                                "Sort By Oldest Date",
                                reportService.sortByOldest(),
                                HttpStatus.OK,
                                LocalDateTime.now()
                        )
                );
    }
}
