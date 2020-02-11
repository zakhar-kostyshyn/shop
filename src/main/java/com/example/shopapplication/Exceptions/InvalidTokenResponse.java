package com.example.shopapplication.Exceptions;

public class InvalidTokenResponse {

    private String tokenProblemMessage;

    public InvalidTokenResponse(String tokenProblemMessage) {
        this.tokenProblemMessage = tokenProblemMessage;
    }

    public String getTokenProblemMessage() {
        return tokenProblemMessage;
    }

    public void setTokenProblemMessage(String tokenProblemMessage) {
        this.tokenProblemMessage = tokenProblemMessage;
    }
}
