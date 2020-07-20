package by.epam.airport_system.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class Plane implements Serializable {
    private String model;
    private String number;
}
