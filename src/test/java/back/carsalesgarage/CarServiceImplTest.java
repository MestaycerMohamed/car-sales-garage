package back.carsalesgarage;

import back.carsalesgarage.entities.Car;
import back.carsalesgarage.repositories.CarRepository;
import back.carsalesgarage.servicesImpl.CarServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceImplTest {
    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    public void testAddCarToCatalog() {
        Car car = new Car();
        when(carRepository.save(car)).thenReturn(car);

        Car savedCar = carService.addCarToCatalog(car);

        assertEquals(car, savedCar);
        verify(carRepository, times(1)).save(car);
    }

    @Test
    public void testGetCarsByFuelTypeAndMaxPrice() {
        String fuelType = "DIESEL";
        long maxPrice = 30000;
        List<Car> cars = new ArrayList<>();
        when(carRepository.findCarsByFuelTypeAndMaxPrice(fuelType, maxPrice)).thenReturn(cars);

        List<Car> result = carService.getCarsByFuelTypeAndMaxPrice(fuelType, maxPrice);

        assertEquals(cars, result);
        verify(carRepository, times(1)).findCarsByFuelTypeAndMaxPrice(fuelType, maxPrice);
    }

    @Test
    public void testGetAllCarMakes() {
        List<String> makes = Arrays.asList("Toyota", "Honda");
        when(carRepository.findAllCarMakes()).thenReturn(makes);

        List<String> result = carService.getAllCarMakes();

        assertEquals(makes, result);
        verify(carRepository, times(1)).findAllCarMakes();
    }

    @Test
    public void testUpdateCarPicture() {
        Long carId = 1L;
        byte[] newPicture = new byte[100];
        Car car = new Car();
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        when(carRepository.save(car)).thenReturn(car);

        carService.updateCarPicture(carId, newPicture);

        assert(newPicture).equals(car.getPicture());
        verify(carRepository, times(1)).findById(carId);
        verify(carRepository, times(1)).save(car);
    }
}
