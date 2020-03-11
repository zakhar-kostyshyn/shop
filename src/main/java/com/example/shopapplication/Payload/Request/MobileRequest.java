package com.example.shopapplication.Payload.Request;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
public class MobileRequest {

    @NotBlank(message = "mobile identifier is required")
    @Size(max = 4)
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

}
