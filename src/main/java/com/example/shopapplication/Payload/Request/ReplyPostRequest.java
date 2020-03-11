package com.example.shopapplication.Payload.Request;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class ReplyPostRequest {

    private String message;

    private String username;

    private String date;

    @NotNull
    private Integer messageParentId;

}
