package com.example.shopapplication.Exceptions;

public class MobilePhoneIdExceptionResponse {

    private String mobileIdentifier;

    public MobilePhoneIdExceptionResponse(String mobileIdentifier) {
        this.mobileIdentifier = mobileIdentifier;
    }

    public String getMobileIdentifier() {
        return mobileIdentifier;
    }

    public void setMobileIdentifier(String mobileIdentifier) {
        this.mobileIdentifier = mobileIdentifier;
    }
}
