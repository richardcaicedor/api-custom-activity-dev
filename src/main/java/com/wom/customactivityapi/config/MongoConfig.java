package com.wom.customactivityapi.config;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    @Autowired
    private VaultConfiguration vaultConfiguration;

    @Value("${spring_data_mongodb_database}")
    private String database;

    @Value("${spring_data_mongodb_host}")
    private String host;

    @Value("${spring_data_mongodb_port}")
    private int port;

    @Bean
    public MongoClient mongoClient() {
        System.out.println(host);
        return MongoClients.create("mongodb://"+host+":27017/"+database+"?authSource="+database);
    }

    @Bean
    MongoTemplate  mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, database);
    }
}
