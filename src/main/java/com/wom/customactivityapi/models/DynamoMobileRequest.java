package com.wom.customactivityapi.models;

import lombok.Data;

import java.util.List;

@Data
public class DynamoMobileRequest {
    private long[] msisdn;
    private String event;
}
