package com.wom.customactivityapi.models;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Data
@Document(collection = "customActivityApiLog")
public class CustomActivityApiLog {
    private Date dateError;
    private String status;
    private String httpError;
    private String error;
}

