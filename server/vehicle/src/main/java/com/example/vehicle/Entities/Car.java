package com.example.vehicle.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "tb_Car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;
    private int year;
    private String color;
    private String carCondition;
    private String image;
    private String type;
    private double price;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private java.sql.Timestamp createdAt; // Timestamp field

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Order> orders;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Report> reports;

    public Car(){}

    public Car(Long id, String model, int year, String color, String carCondition, String image, String type, double price, Timestamp createdAt, List<Order> orders, List<Report> reports) {
        this.id = id;
        this.model = model;
        this.year = year;
        this.color = color;
        this.carCondition = carCondition;
        this.image = image;
        this.type = type;
        this.price = price;
        this.createdAt = createdAt;
        this.orders = orders;
        this.reports = reports;
    }
}
