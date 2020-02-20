package com.example.shopapplication.Payload.Request;

import lombok.Getter;
import javax.validation.constraints.NotBlank;


@Getter
public class ChatRequest {

    private String message;

    private String username;

    @NotBlank
    private String phoneId;

}
