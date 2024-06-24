package com.example.vehicle.Service;

import com.example.vehicle.Entities.Car;
import com.example.vehicle.Entities.Customer;
import com.example.vehicle.Entities.Order;
import com.example.vehicle.Entities.Report;
import com.example.vehicle.Repository.CarRepo;
import com.example.vehicle.Repository.CustomerRepo;
import com.example.vehicle.Repository.OrderRepo;
import com.example.vehicle.Repository.ReportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class OrderService {
    private final OrderRepo orderRepo;
    private final ReportRepo reportRepo;
    @Autowired
    public OrderService(OrderRepo orderRepo, ReportRepo reportRepo) {
        this.orderRepo = orderRepo;
        this.reportRepo = reportRepo;
    }

    public String addOrder(String customerName, String customerPhone, String carName, double carPrice) {
        Report report = new Report();
        report.setDescription("PAID");
        report.setCarName(carName);
        report.setCarPrice(carPrice);
        report.setCustomerName(customerName);
        report.setCustomerPhone(customerPhone);
        reportRepo.save(report);
        return "Order Success!";
    }

    public List<Order> listOrder() {
        return orderRepo.findAll();
    }

    public String deleteOrder(long orderID) {
        orderRepo.deleteById(orderID);
        return "Delete success";
    }

}
