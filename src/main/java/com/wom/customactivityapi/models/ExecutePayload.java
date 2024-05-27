package com.wom.customactivityapi.models;

import lombok.Data;

import java.util.Map;

@Data
public class ExecutePayload {
    private String mode;

    private String activityId;

    private String activityObjectID;

    private String definitionInstanceId;

    private String keyValue;

    private Map<String, String>[] outArguments;

    private Map<String, String>[] inArguments;

    private String activityInstanceId;

    private String journeyId;

    private int iat;
    private int exp;
}
