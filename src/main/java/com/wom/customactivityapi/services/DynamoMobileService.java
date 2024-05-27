package com.wom.customactivityapi.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wom.customactivityapi.config.VaultConfiguration;
import com.wom.customactivityapi.models.CustomActivityApiLog;
import com.wom.customactivityapi.models.DynamoMobileRequest;
import com.wom.customactivityapi.models.DynamoMobileResponse;
import com.wom.customactivityapi.util.ConfigValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

@Service
public class DynamoMobileService {

    @Autowired
    private VaultConfiguration vaultConfiguration;
    @Autowired
    private CustomActivityApiLogService customActivityApiLogService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ConfigValues configSpecificValues;

    @Value("${dynamo_mobile_bearer_token}")
    private String dynamoMobileBearerToken;


    public enum Status {
        Executed
    }

    public DynamoMobileResponse consumeService(String eventId, long[] phoneNumbers) {
        DynamoMobileRequest request = createRequest(eventId, phoneNumbers);
        HttpEntity<DynamoMobileRequest> entity = createHttpEntity(request);

        ResponseEntity<String> response = sendRequest(entity);

        DynamoMobileResponse dynamoMobileResponse = convertResponse(response);

        if (!isRequestExecuted(dynamoMobileResponse)) {
            logError(response, dynamoMobileResponse);
        }

        return dynamoMobileResponse;
    }

    private DynamoMobileRequest createRequest(String eventId, long[] phoneNumbers) {
        DynamoMobileRequest request = new DynamoMobileRequest();
        request.setMsisdn(phoneNumbers);
        request.setEvent(eventId);
        return request;
    }

    private HttpEntity<DynamoMobileRequest> createHttpEntity(DynamoMobileRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        String dynamomobileBearerToken = vaultConfiguration.getDynamoMobileBearerToken();
        System.out.println("dynamomobileBearerToken VAULT: " + dynamomobileBearerToken);
        headers.set("Authorization", "Bearer " + dynamoMobileBearerToken);
        return new HttpEntity<>(request, headers);
    }

    private ResponseEntity<String> sendRequest(HttpEntity<DynamoMobileRequest> entity) {

        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(configSpecificValues.getDynamomobileUri(), HttpMethod.POST, entity, String.class);
        } catch (Exception e) {
            logError(e);
            throw e;
        }
        return response;
    }

    private void logError(Exception e) {
        CustomActivityApiLog customActivityApiLog = new CustomActivityApiLog();
        customActivityApiLog.setDateError(new Date());
        customActivityApiLog.setError(e.getMessage());
        customActivityApiLogService.saveLog(customActivityApiLog);
        System.out.println("ERROR CON EL SERVICIO DE DYNAMO: " + e.getMessage());
    }

    private void logError(ResponseEntity<String> response, DynamoMobileResponse dynamoMobileResponse) {
        CustomActivityApiLog customActivityApiLog = new CustomActivityApiLog();
        customActivityApiLog.setDateError(new Date());
        customActivityApiLog.setHttpError(response.getStatusCode().toString());
        customActivityApiLog.setError(dynamoMobileResponse.getError());
        customActivityApiLog.setStatus(dynamoMobileResponse.getStatus());
        customActivityApiLogService.saveLog(customActivityApiLog);
        System.out.println("ERROR CON EL SERVICIO DE DYNAMO: " + dynamoMobileResponse.getError());
    }

    private boolean isRequestExecuted(DynamoMobileResponse dynamoMobileResponse) {
        return dynamoMobileResponse != null ? dynamoMobileResponse.getStatus().equals(Status.Executed) : false;
    }

    private DynamoMobileResponse convertResponse(ResponseEntity<String> response)
    {
        try {
            // Crear un objeto ObjectMapper
            ObjectMapper mapper = new ObjectMapper();

            // Convertir la cadena JSON en un objeto Response
            DynamoMobileResponse dynamoResponse = mapper.readValue(response.getBody(), DynamoMobileResponse.class);
            dynamoResponse.setStatusCode(response.getStatusCodeValue());
            return  dynamoResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
