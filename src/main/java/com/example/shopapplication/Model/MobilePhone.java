package com.example.shopapplication.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Blob;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

    @Lob
    private String image;

    @OneToOne(cascade = CascadeType.ALL)
    private Chat chat;

    public MobilePhone(String mobileIdentifier, String brand, String model, String graduationYear, Double price, String image) {
        this.mobileIdentifier = mobileIdentifier;
        this.brand = brand;
        this.model = model;
        this.graduationYear = graduationYear;
        this.price = price;
        this.image = image;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobileIdentifier() {
        return mobileIdentifier;
    }

    public void setMobileIdentifier(String mobileIdentifier) {
        this.mobileIdentifier = mobileIdentifier;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(String graduationYear) {
        this.graduationYear = graduationYear;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
