package com.example.shopapplication.Model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WrapperRequest {

    private String mobileIdentifier;
    private String brand;

    public WrapperRequest(){}

    public WrapperRequest(String mobileIdentifier, String brand) {
        this.mobileIdentifier = mobileIdentifier;
        this.brand = brand;
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
}
