package back.carsalesgarage.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Car implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carId", nullable = false)
    private Long carId;
    @Column
    private String make;
    @Column
    private String model;
    @Column
    private Date registrationDate;
    @Column
    private Long price;
    @Setter(AccessLevel.NONE)
    @Column
    private String fuelType; //(DIESEL or ELECTRIC or HYBRID)
    @Column
    private String mileage;
    @Setter(AccessLevel.NONE)
    @Column
    private String transmission;//(MANUAL or SEMI AUTOMATIC or AUTOMATIC)
    @Lob //for storing large objects
    @Column
    private byte[] picture;

    public void setFuelType(String fuelType) {
        if(fuelType != "DIESEL" && fuelType != "ELECTRIC" && fuelType != "HYBRID")
            return;
        this.fuelType = fuelType;
    }

    public void setTransmission(String transmission) {
        if(transmission != "MANUAL" && transmission != "SEMI AUTOMATIC" && transmission != "AUTOMATIC")
            return;
        this.transmission = transmission;
    }
}
