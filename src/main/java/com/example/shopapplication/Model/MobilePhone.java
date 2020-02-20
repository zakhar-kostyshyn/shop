package com.example.shopapplication.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
public class MobilePhone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "mobile identifier is required")
    @Size(max = 4)
    @Column(updatable = false, unique = true)
    private String mobileIdentifier;

    @NotBlank(message = "name of brand mobile is required")
    @Size(max = 25)
    private String brand;

    @NotBlank(message = "name of model mobile is required")
    @Size(max = 20)
    private String model;

    @NotBlank(message = "field must be filled")
    private String graduationYear;
        
    private Double price;

    @OneToOne(cascade = CascadeType.ALL)
    private Chat chat;

//    public String toString() {
//        return "\nident : " + mobileIdentifier + " \nbrand : " + brand;
//    }
}
