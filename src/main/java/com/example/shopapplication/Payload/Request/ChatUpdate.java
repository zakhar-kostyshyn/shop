package com.example.shopapplication.Payload.Request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class ChatUpdate {

    @NotBlank
    private String messageId;

    @NotBlank
    private String message;

    @NotBlank
    private String username;

    @NotBlank
    private String date;

}
