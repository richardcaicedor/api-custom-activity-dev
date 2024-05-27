package com.wom.customactivityapi.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Archivo de configuraciones
 */
@Configuration
@Component
@Getter
public class ConfigValues {
    @Value("${dynamomobileuri}")
    private String dynamomobileUri;

}