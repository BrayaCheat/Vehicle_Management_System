package com.example.vehicle.Controller;

import com.example.vehicle.Entities.ApiResponse.APIResponse;
import com.example.vehicle.Entities.Customer;
import com.example.vehicle.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @CrossOrigin(origins = "http://localhost:3000")

    @GetMapping
    public ResponseEntity<APIResponse<List<Customer>>> listCustomer() {
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse<>("List All Customers Successfully", customerService.listCustomer(), HttpStatus.OK, LocalDateTime.now()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<Customer>> getCustomer(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse<>("Get Customer By ID: " + id + " Successfully!", customerService.getCustomer(id), HttpStatus.OK, LocalDateTime.now()));
    }

    @PostMapping
    public ResponseEntity<APIResponse<Customer>> addCustomer(@ModelAttribute Customer customer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse<>("Creating New Customer Successfully", customerService.addCustomer(customer), HttpStatus.CREATED, LocalDateTime.now()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<Customer>> updateCustomer(@PathVariable long id, @ModelAttribute Customer customer) {
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse<>("Updating Customer With ID: " + id + " Successfully", customerService.updateCustomer(id, customer), HttpStatus.OK, LocalDateTime.now()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteCustomer(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse<>("Deleting Customer With ID: " + id + " Successfully", customerService.deleteCustomer(id), HttpStatus.OK, LocalDateTime.now()));
    }

    @GetMapping("/search")
    public ResponseEntity<APIResponse<List<Customer>>> search(@RequestParam String keyword) {
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse<>("Searching Customer Successfully", customerService.search(keyword), HttpStatus.OK, LocalDateTime.now()));
    }

    @GetMapping("/sortByName")
    public ResponseEntity<APIResponse<List<Customer>>> sortByName() {
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse<>("Sort Customer By Name Successfully", customerService.sortByName(), HttpStatus.OK, LocalDateTime.now()));
    }
}
