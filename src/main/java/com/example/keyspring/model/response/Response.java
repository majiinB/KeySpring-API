package com.example.keyspring.model.response;

import lombok.Data;

@Data
public class Response {
    private String status;
    private String message;
    private Object data;
}
