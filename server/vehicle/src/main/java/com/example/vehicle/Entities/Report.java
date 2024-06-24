package com.example.vehicle.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@Builder
@Table(name = "tb_Report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String customerName;
    private String customerPhone;
    private String carName;
    private double carPrice;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private java.sql.Timestamp createdAt; // Timestamp field

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    @ManyToOne
    @JoinColumn(name = "car_id")
    @JsonBackReference
    private Car car;

    public Report() {

    }

    public Report(Long id, String description, String customerName, String customerPhone, String carName, double carPrice, Timestamp createdAt, Order order, Car car) {
        this.id = id;
        this.description = description;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.carName = carName;
        this.carPrice = carPrice;
        this.createdAt = createdAt;
        this.order = order;
        this.car = car;
    }
}
