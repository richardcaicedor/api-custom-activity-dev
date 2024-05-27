package com.wom.customactivityapi.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wom.customactivityapi.exceptions.ValidationException;
import com.wom.customactivityapi.models.ActivityResult;
import com.wom.customactivityapi.models.DynamoMobileResponse;
import com.wom.customactivityapi.models.ExecutePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ActivityService {


    private final DynamoMobileService dynamoMobileService;

    private static final Set<String> REQUIRED_FIELDS = new HashSet<>(Arrays.asList("phoneNumber", "message", "eventId"));

    @Autowired
    public ActivityService(DynamoMobileService dynamoMobileService) {
        this.dynamoMobileService = dynamoMobileService;
    }

    public ActivityResult processRequest(ExecutePayload request) throws JsonProcessingException {
        // Realizar las validaciones
        validateInArguments(request.getInArguments());
        String eventId = findValueForKey(request,"eventId");
        long[] phoneNumbers = Arrays.stream(findValueForKey(request,"phoneNumber").split(","))
                .mapToLong(Long::parseLong).toArray();
        DynamoMobileResponse dynamoMobileResponse = dynamoMobileService.consumeService(eventId,phoneNumbers);
        ActivityResult activityResult = new ActivityResult();
        activityResult.setBranchResult(dynamoMobileResponse.getStatus().equals("Executed") ? "sent" : "notsent");
        activityResult.setRequestId(dynamoMobileResponse.getRequestId());
        activityResult.setStatus(dynamoMobileResponse.getStatus());
        activityResult.setError(dynamoMobileResponse.getError());
        activityResult.setStatusCode(dynamoMobileResponse.getStatusCode());
        return  activityResult;
    }

    private String findValueForKey(ExecutePayload request,String key) {
        return Arrays.stream(request.getInArguments())
                .filter(map -> map.containsKey(key))
                .findFirst()
                .map(map -> map.get(key))
                .orElse(null);
    }

    private void validateInArguments(Map<String, String>[] inArguments) {
        Set<String> missingFields = new HashSet<>(REQUIRED_FIELDS);
        for (Map<String, String> argument : inArguments) {
            missingFields.removeAll(argument.keySet());
        }
        if (!missingFields.isEmpty()) {
            throw new ValidationException("Missing required fields in inArguments: " + missingFields);
        }
    }

}
