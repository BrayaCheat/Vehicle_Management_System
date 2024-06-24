package com.example.vehicle.Repository;

import com.example.vehicle.Entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepo extends JpaRepository<Car, Long> {
    @Query(value = "SELECT * FROM tb_car WHERE model LIKE %?1%", nativeQuery = true)
    public List<Car> search(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM tb_car WHERE type LIKE %?1%", nativeQuery = true)
    public List<Car> filter(@Param("filter") String type);

    @Query(value = "SELECT * FROM tb_car ORDER BY model ASC", nativeQuery = true)
    public List<Car> sortByName();

    @Query(value = "SELECT * FROM tb_car ORDER BY model ASC", nativeQuery = true)
    public List<Car> sortByModel();

    @Query(value = "SELECT * FROM tb_car ORDER BY price ASC", nativeQuery = true)
    public List<Car> sortByPriceASC();

    @Query(value = "SELECT * FROM tb_car ORDER BY price DESC", nativeQuery = true)
    public List<Car> sortByPriceDESC();

    @Query(value = "SELECT * FROM tb_car ORDER BY model ASC", nativeQuery = true)
    public List<Car> sortByCondition();

    @Query(value = "SELECT * FROM tb_car ORDER BY year ASC", nativeQuery = true)
    public List<Car> sortByYearASC();

    @Query(value = "SELECT * FROM tb_car ORDER BY year DESC", nativeQuery = true)
    public List<Car> sortByYearDESC();
}
