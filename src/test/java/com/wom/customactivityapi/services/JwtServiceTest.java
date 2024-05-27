package com.wom.customactivityapi.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@TestPropertySource(locations = "classpath:test.properties")

class JwtServiceTest {
//    @Autowired
//    private JwtService jwtService;
//
//    @Value("${security.jwt.secret-key}")
//    private String secretKey;
//
//    @Value("${security.jwt.expiration-time}")
//    private long jwtExpiration;
//
//    @Test
//    void generateToken() throws JsonProcessingException {
//        String json = "{"
//                + "\"mode\": \"modo_de_ejemplo\","
//                + "\"activityId\": \"id_de_actividad_ejemplo\","
//                + "\"activityObjectID\": \"id_del_objeto_de_actividad_ejemplo\","
//                + "\"definitionInstanceId\": \"id_de_instancia_de_definicion_ejemplo\","
//                + "\"keyValue\": \"valor_de_clave_ejemplo\","
//                + "\"outArguments\": [],"
//                + "\"inArguments\": ["
//                + "{ \"phoneNumber\": \"573118499374,573156755437\" },"
//                + "{ \"message\": \"Campaña de prueba\" },"
//                + "{ \"eventId\": \"test\" }"
//                + "],"
//                + "\"activityInstanceId\": \"id_de_instancia_de_actividad_ejemplo\","
//                + "\"journeyId\": \"id_de_viaje_ejemplo\""
//                + "}";
//
//        ObjectMapper mapper = new ObjectMapper();
//        Map<String, Object> map = mapper.readValue(json, Map.class);
//
//        String token = jwtService.generateToken(map);
//        assertNotNull(token);
//    }
}