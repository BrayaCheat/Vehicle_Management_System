package com.example.vehicle.Service;

import com.example.vehicle.Entities.Car;
import com.example.vehicle.Repository.CarRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@Validated
public class CarService {
    private final CarRepo carRepo;

    @Autowired
    public CarService(CarRepo carRepo) {
        this.carRepo = carRepo;
    }

    @Value("${upload.dir}")
    private String uploadDir;

    private String saveFileLocally(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("File is empty");
        }

        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir, fileName);
//            Files.write(uploadPath, file.getBytes());
            Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new IOException("Could not save file: " + e.getMessage(), e);
        }
    }

        @Async
        public Car addCar(Car car, MultipartFile file) throws IOException {
        String fileName = saveFileLocally(file);
        car.setImage(fileName); // Set the image file name in the Car object
        if(car.getImage().isEmpty()) {
            throw new IOException("File is empty!");
        }
        return carRepo.save(car);
    }

    public List<Car> listCars() {
        return carRepo.findAll();
    }

    public Car getCar(Long id) {
        return carRepo.findById(id).orElse(null);
    }

    public Car updateCar(Long id, Car car, MultipartFile file) throws IOException {
        Car existingCar = carRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + id));
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        String fileName = saveFileLocally(file);
        existingCar.setImage(fileName);
        existingCar.setModel(car.getModel());
        existingCar.setYear(car.getYear());
        existingCar.setColor(car.getColor());
        existingCar.setCarCondition(car.getCarCondition());
        existingCar.setType(car.getType());
        existingCar.setPrice(car.getPrice());
        return carRepo.save(existingCar);
    }

    public String deleteCar(long id) {
        carRepo.deleteById(id);
        return "Delete success: " + id;
    }

    public List<Car> search(String keyword) {
        return carRepo.search(keyword);
    }

    public List<Car> filter(String type) {
        return carRepo.filter(type);
    }

    public List<Car> sortByModel() {
        return carRepo.sortByModel();
    }

    public List<Car> sortByPriceASC() {
        return carRepo.sortByPriceASC();
    }

    public List<Car> sortByPriceDESC() {
        return carRepo.sortByPriceDESC();
    }

    public List<Car> sortByCondition() {
        return carRepo.sortByCondition();
    }

    public List<Car> sortByYearASC() {
        return carRepo.sortByYearASC();
    }

    public List<Car> sortByYearDESC() {
        return carRepo.sortByYearDESC();
    }
}
