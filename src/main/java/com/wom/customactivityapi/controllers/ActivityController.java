package com.wom.customactivityapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wom.customactivityapi.exceptions.ValidationException;
import com.wom.customactivityapi.models.ActivityResult;
import com.wom.customactivityapi.models.ExecutePayload;
import com.wom.customactivityapi.services.ActivityService;
import com.wom.customactivityapi.services.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activity")
public class ActivityController {
    private final ActivityService activityService;
    private final JwtService jwtService;

    @Autowired
    public ActivityController(ActivityService activityService, JwtService jwtService) {
        this.activityService = activityService;
        this.jwtService = jwtService;
    }
    @PostMapping("/execute")
    public ResponseEntity<?> execute(@RequestBody String request) {
        try {
            ExecutePayload executePayload = convertPayloadToExecutePayload(request);
            return processRequest(executePayload);
        } catch (ValidationException ex) {
            return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    } 

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody String payload) {
        return new ResponseEntity<>("Save", HttpStatus.OK);
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publish (@RequestBody String payload) {
        return new ResponseEntity<>("Publish", HttpStatus.OK);
    }

    @PostMapping("/stop")
    public ResponseEntity<String> stop (@RequestBody String payload) {
        return new ResponseEntity<>("Stop", HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validate (@RequestBody String payload) {
        return new ResponseEntity<>("Validate", HttpStatus.OK);
    }

    private ExecutePayload convertPayloadToExecutePayload(String payload) throws JsonProcessingException {
        Claims claims = jwtService.extractAllClaims(payload);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(claims);
        return mapper.readValue(json, ExecutePayload.class);
    }

    private ResponseEntity<?> processRequest(ExecutePayload executePayload) throws JsonProcessingException {
        Object response = activityService.processRequest(executePayload);
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<?> buildResponseEntity(HttpStatus status, String message) {
        ActivityResult activityResult = buildResponseError(status,message);
        return ResponseEntity.status(status).body(activityResult);
    }

    private ActivityResult buildResponseError(HttpStatus status,String error)
    {
        ActivityResult activityResult = new ActivityResult();
        activityResult.setBranchResult("notsent");
        activityResult.setStatus("CustomApiError");
        activityResult.setError(error);
        activityResult.setStatusCode(status.value());
        return  activityResult;
    }

}
