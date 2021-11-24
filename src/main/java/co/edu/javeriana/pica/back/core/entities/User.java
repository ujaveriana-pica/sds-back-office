package co.edu.javeriana.pica.back.core.entities;

import lombok.Data;

@Data
public class User {
    private String userId;
    private String username;
    private String name;
    private String lastName;
    private String email;
    private String status;
    private String rol;
}