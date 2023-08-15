package back.carsalesgarage;

import back.carsalesgarage.entities.Car;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CarTest {

    @Test
    public void testSetFuelType() {
        Car car = new Car();

        car.setFuelType("DIESEL");
        assertEquals("DIESEL", car.getFuelType());

        car.setFuelType("ELECTRIC");
        assertEquals("ELECTRIC", car.getFuelType());

        car.setFuelType("HYBRID");
        assertEquals("HYBRID", car.getFuelType());

        car.setFuelType("GAS");// Test with invalid value
        assertEquals("HYBRID", car.getFuelType());
    }

    @Test
    public void testSetTransmission() {
        Car car = new Car();

        car.setTransmission("MANUAL");
        assertEquals("MANUAL", car.getTransmission());

        car.setTransmission("SEMI AUTOMATIC");
        assertEquals("SEMI AUTOMATIC", car.getTransmission());

        car.setTransmission("AUTOMATIC");
        assertEquals("AUTOMATIC", car.getTransmission());

        car.setTransmission("AUTO");// Test with invalid value
        assertEquals("AUTOMATIC", car.getTransmission());
    }
}