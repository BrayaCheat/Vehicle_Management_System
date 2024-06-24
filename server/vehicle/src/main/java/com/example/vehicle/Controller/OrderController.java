package com.example.vehicle.Controller;

import com.example.vehicle.Entities.ApiResponse.APIResponse;
import com.example.vehicle.Entities.Car;
import com.example.vehicle.Entities.Order;
import com.example.vehicle.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @CrossOrigin(origins = "http://localhost:3000")

    @PostMapping
    public ResponseEntity<APIResponse<String>> addOrder(@RequestParam String customerName, @RequestParam String customerPhone, @RequestParam String carName, @RequestParam double carPrice) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new APIResponse<>(
                                "Order Successfully",
                                orderService.addOrder(customerName, customerPhone, carName, carPrice),
                                HttpStatus.CREATED,
                                LocalDateTime.now()
                        )
                );
    }
}
