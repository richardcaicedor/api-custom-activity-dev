package com.wom.customactivityapi.models;

import lombok.Data;

@Data
public class DynamoMobileResponse {
    private int statusCode;
    private String requestId;
    private String status;
    private String message;
    private String error;
}
