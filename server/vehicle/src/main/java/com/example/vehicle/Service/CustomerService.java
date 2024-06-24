package com.example.vehicle.Service;

import com.example.vehicle.Entities.Customer;
import com.example.vehicle.Repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class CustomerService {
    private final CustomerRepo customerRepo;
    @Autowired
    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public Customer addCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    public List<Customer> listCustomer() {
        return customerRepo.findAll();
    }

    public Customer getCustomer(Long id) {
        return customerRepo.findById(id).orElse(null);
    }

    public Customer updateCustomer(long id, Customer customer) {
        Customer update = customerRepo.findById(id).orElse(null);
        assert update != null;
        update.setName(customer.getName());
        update.setEmail(customer.getEmail());
        update.setPhone(customer.getPhone());
        return customerRepo.save(update);
    }

    public String deleteCustomer(long id) {
        customerRepo.deleteById(id);
        return "Delete success: " + id;
    }

    public List<Customer> search(String keyword) {
        return customerRepo.search(keyword.toLowerCase());
    }

    public List<Customer> sortByName() {
        return customerRepo.sortByName();
    }

    public List<Customer> sortByDate(){ return customerRepo.sortByDate();}

}
