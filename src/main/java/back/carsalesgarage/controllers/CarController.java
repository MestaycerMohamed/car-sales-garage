package back.carsalesgarage.controllers;

import back.carsalesgarage.entities.Car;
import back.carsalesgarage.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {
    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/add")
    public ResponseEntity<Car> addCarToCatalog(@RequestBody Car car) {
        Car savedCar = carService.addCarToCatalog(car);
        return ResponseEntity.ok(savedCar);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Car>> getCarsByFuelTypeAndMaxPrice(
            @RequestParam String fuelType, @RequestParam long maxPrice) {
        List<Car> cars = carService.getCarsByFuelTypeAndMaxPrice(fuelType, maxPrice);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/makes")
    public ResponseEntity<List<String>> getAllCarMakes() {
        List<String> makes = carService.getAllCarMakes();
        return ResponseEntity.ok(makes);
    }

    @PutMapping("/{carId}/update-picture")
    public ResponseEntity<String> updateCarPicture(
            @PathVariable Long carId, @RequestParam("newPicture") MultipartFile newPicture) {
        try {
            String msg = carService.updateCarPicture(carId, newPicture.getBytes());
            if(msg.indexOf("Error") > -1)
                return ResponseEntity.badRequest().body(msg);
            return ResponseEntity.ok(msg);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating car picture: " + e.getMessage());
        }
    }
}
