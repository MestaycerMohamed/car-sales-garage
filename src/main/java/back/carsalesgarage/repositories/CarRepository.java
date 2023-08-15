package back.carsalesgarage.repositories;

import back.carsalesgarage.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<Car,Long> {

    /**displays all the cars by Fuel type and Max Price**/
    @Query("SELECT c FROM Car c WHERE c.fuelType = :fuelType AND c.price <= :maxPrice")
    List<Car> findCarsByFuelTypeAndMaxPrice(@Param("fuelType") String fuelType, @Param("maxPrice") long maxPrice);

    /**displays all the make available in the catalog**/
    @Query("SELECT DISTINCT c.make FROM Car c")
    List<String> findAllCarMakes();

    /**update a car picture**/
    @Query("UPDATE Car c SET c.picture = :newPicture WHERE c.carId = :carId")
    void updateCarPicture(@Param("carId") Long carId, @Param("newPicture") byte[] newPicture);

}
