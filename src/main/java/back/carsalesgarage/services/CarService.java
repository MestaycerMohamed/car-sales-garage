package back.carsalesgarage.services;

import back.carsalesgarage.entities.Car;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CarService {
    public Car addCarToCatalog(Car car);
    public List<Car> getCarsByFuelTypeAndMaxPrice(String fuelType, long maxPrice);
    public List<String> getAllCarMakes();
    public String updateCarPicture(Long carId, byte[] newPicture);
}
