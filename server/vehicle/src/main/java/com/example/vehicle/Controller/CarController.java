package com.example.vehicle.Controller;

import com.example.vehicle.Entities.ApiResponse.APIResponse;
import com.example.vehicle.Entities.Car;
import com.example.vehicle.Service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/car")
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @CrossOrigin(origins = "http://localhost:3000")

    @GetMapping
    public ResponseEntity<APIResponse<List<Car>>> listCar() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new APIResponse<>(
                                "Fetching All Vehicles Successfully!",
                                carService.listCars(),
                                HttpStatus.OK,
                                LocalDateTime.now()
                        )
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<Car>> getCar(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new APIResponse<>(
                                "Fetching Vehicle Id: " + id + " Successfully!",
                                carService.getCar(id),
                                HttpStatus.OK,
                                LocalDateTime.now()
                        )
                );
    }

    @PostMapping
    public ResponseEntity<APIResponse<Car>> addCar(@ModelAttribute Car car, @RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(
                            new APIResponse<>(
                                    "Creating New Vehicle Successfully!",
                                    carService.addCar(car, file),
                                    HttpStatus.OK,
                                    LocalDateTime.now()
                            )
                    );
        } catch (IOException e) {
            throw new RuntimeException("Error uploading file: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<Car>> updateCar(@PathVariable long id, @ModelAttribute Car car, @RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(
                            new APIResponse<>(
                                    "Updating Vehicle With ID: " + id + " Successfully!",
                                    carService.updateCar(id, car, file),
                                    HttpStatus.OK,
                                    LocalDateTime.now()
                            )
                    );
        } catch (IOException e) {
            throw new RuntimeException("Error uploading file: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteCar(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new APIResponse<>(
                                "Deleting Vehicle ID:" + id + " Successfully!",
                                carService.deleteCar(id),
                                HttpStatus.OK,
                                LocalDateTime.now()
                        )
                );
    }

    @GetMapping("/search")
    public ResponseEntity<APIResponse<List<Car>>> search(@RequestParam String keyword) {
        List<Car> cars = carService.search(keyword);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new APIResponse<>(
                                "Searching All Vehicles",
                                carService.search(keyword),
                                HttpStatus.OK,
                                LocalDateTime.now()
                        )
                );
    }

    @GetMapping("/filter")
    public ResponseEntity<APIResponse<List<Car>>> filter(@RequestParam String type) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new APIResponse<>(
                                "Filter Vehicles By Type: " + type,
                                carService.filter(type),
                                HttpStatus.OK,
                                LocalDateTime.now()
                        )
                );
    }

    @GetMapping("/sortByPriceASC")
    public ResponseEntity<APIResponse<List<Car>>> sortByPriceASC() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new APIResponse<>(
                                "Sort Vehicle By Price Ascending",
                                carService.sortByPriceASC(),
                                HttpStatus.OK,
                                LocalDateTime.now()
                        )
                );
    }

    @GetMapping("/sortByPriceDESC")
    public ResponseEntity<APIResponse<List<Car>>> sortByPriceDESC() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new APIResponse<>(
                                "Sort Vehicle By Price Descending",
                                carService.sortByPriceDESC(),
                                HttpStatus.OK,
                                LocalDateTime.now()
                        )
                );
    }

    @GetMapping("/sortByYearASC")
    public ResponseEntity<APIResponse<List<Car>>> sortByYearASC() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new APIResponse<>(
                                "Sort Vehicle By Year Ascending",
                                carService.sortByYearASC(),
                                HttpStatus.OK,
                                LocalDateTime.now()
                        )
                );
    }

    @GetMapping("/sortByYearDESC")
    public ResponseEntity<APIResponse<List<Car>>> sortByYearDESC() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new APIResponse<>(
                                "Sort Vehicle By Year Descending",
                                carService.sortByYearDESC(),
                                HttpStatus.OK,
                                LocalDateTime.now()
                        )
                );
    }
}
