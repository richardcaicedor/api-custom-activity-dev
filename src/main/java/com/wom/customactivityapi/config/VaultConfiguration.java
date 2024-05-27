package com.wom.customactivityapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("api")
public class VaultConfiguration {

    private String dynamoMobileBearerToken;
    private String securityJwtSecretKey;
    private String userDB;
    private String passwordDB;
}
