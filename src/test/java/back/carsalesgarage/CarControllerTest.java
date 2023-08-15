package back.carsalesgarage;

import back.carsalesgarage.controllers.CarController;
import back.carsalesgarage.entities.Car;
import back.carsalesgarage.services.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class CarControllerTest {

    @InjectMocks
    private CarController carController;

    @Mock
    private CarService carService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }

    @Test
    public void testAddCarToCatalog() throws Exception {
        Car car = new Car();
        when(carService.addCarToCatalog(any(Car.class))).thenReturn(car);

        mockMvc.perform(post("/cars/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(car).getBytes())) // Use .content() with byte[]
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.make").value(car.getMake()));

        verify(carService, times(1)).addCarToCatalog(any(Car.class));
    }
    @Test
    public void testGetCarsByFuelTypeAndMaxPrice() throws Exception {
        String fuelType = "DIESEL";
        long maxPrice = 30000;
        List<Car> cars = new ArrayList<>();
        when(carService.getCarsByFuelTypeAndMaxPrice(fuelType, maxPrice)).thenReturn(cars);

        mockMvc.perform(get("/cars/filter")
                        .param("fuelType", fuelType)
                        .param("maxPrice", String.valueOf(maxPrice)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(carService, times(1)).getCarsByFuelTypeAndMaxPrice(fuelType, maxPrice);
    }

    @Test
    public void testGetAllCarMakes() throws Exception {
        List<String> makes = Arrays.asList("Toyota", "Honda");
        when(carService.getAllCarMakes()).thenReturn(makes);

        mockMvc.perform(get("/cars/makes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(carService, times(1)).getAllCarMakes();
    }

    @Test
    public void testUpdateCarPicture() throws Exception {
        Long carId = 1L;
        byte[] newPictureBytes = "TestPicture".getBytes(); // Example picture bytes
        MockMultipartFile newPicture = new MockMultipartFile(
                "newPicture", "image.jpg", "image/jpeg", newPictureBytes);
        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/cars/{carId}/update-picture", carId);
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
        });
        when(carService.updateCarPicture(carId, newPictureBytes)).thenReturn("Car picture updated successfully");
        mockMvc.perform(builder
                        .file(newPicture))
                .andExpect(status().isOk())
                .andExpect(content().string("Car picture updated successfully"));

        verify(carService, times(1)).updateCarPicture(eq(carId), any(byte[].class));
    }

    @Test
    public void testUpdateCarPictureNotFound() throws Exception {
        Long carId = 1L;
        byte[] newPictureBytes = "TestPicture".getBytes(); // Example picture bytes
        MockMultipartFile newPicture = new MockMultipartFile(
                "newPicture", "image.jpg", "image/jpeg", newPictureBytes);
        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/cars/{carId}/update-picture", carId);
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
        });
        when(carService.updateCarPicture(carId, newPictureBytes)).thenReturn("Error updating car picture: car not found");
        mockMvc.perform(builder
                        .file(newPicture))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error updating car picture: car not found"));

        verify(carService, times(1)).updateCarPicture(eq(carId), any(byte[].class));
    }
}
