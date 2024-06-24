package com.example.vehicle.Repository;

import com.example.vehicle.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    @Query(value = "SELECT * FROM tb_customer WHERE name LIKE %?1%", nativeQuery = true)
    public List<Customer> search(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM tb_customer ORDER BY name ASC", nativeQuery = true)
    public List<Customer> sortByName();

    @Query(value = "SELECT * FROM tb_customer ORDER BY created_at ASC", nativeQuery = true)
    public List<Customer> sortByDate();

}
