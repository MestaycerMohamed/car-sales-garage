package back.carsalesgarage.servicesImpl;

import back.carsalesgarage.entities.Car;
import back.carsalesgarage.repositories.CarRepository;
import back.carsalesgarage.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car addCarToCatalog(Car car) {
        return carRepository.save(car);
    }

    public List<Car> getCarsByFuelTypeAndMaxPrice(String fuelType, long maxPrice) {
        return carRepository.findCarsByFuelTypeAndMaxPrice(fuelType, maxPrice);
    }

    public List<String> getAllCarMakes() {
        return carRepository.findAllCarMakes();
    }

    public String updateCarPicture(Long carId, byte[] newPicture) {
        Car car = carRepository.findById(carId).orElse(null);
        if (car != null) {
            car.setPicture(newPicture);
            carRepository.save(car);
            return "Car picture updated successfully";
        }
        return "Error updating car picture: car not found";
    }
}
