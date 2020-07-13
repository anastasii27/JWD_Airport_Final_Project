package by.epam.tr.bean;

import lombok.Builder;
import lombok.Data;
import java.io.Serializable;

@Data
@Builder
public class User implements Serializable{
    private String login;
    private String password;
    private String role;
    private String name;
    private String surname;
    private String email;
    private String careerStartYear;
}

