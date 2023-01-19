package com.yellow.offer.Application.Configuration;

// I have commented out mongo configuration to stop MongoSocketOpenException
// If u want to test mongo uncomment this file, mongo dependency in pom.xml & mongo container in docker-compose.yml

//import com.mongodb.ConnectionString;
//import com.mongodb.MongoClientSettings;
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.data.mongodb.core.MongoTemplate;


//@Configuration
//@Slf4j
//@Profile("usingMongoProfile")
//@ConditionalOnExpression("${mongo.enabled:true}")
//public class OptionalMongoAsDBConfig {
//    @Value("${mongo.asDb.hostname}")
//    private String hostname;
//    @Value("${mongo.asDb.port}")
//    private String port;
//    @Value("${mongo.asDb.databaseName}")
//    private String databaseName;
//    @Bean
//    @Profile("usingMongoProfile")
//    public MongoClient mongoClient() {
//
//        final ConnectionString connectionString = new ConnectionString("mongodb://"+hostname+":"+port+"/"+databaseName);
//        final MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
//                .applyConnectionString(connectionString)
//                .build();
//        return MongoClients.create(mongoClientSettings);
//    }
//    @Bean
//    @Profile("usingMongoProfile")
//    public MongoTemplate mongoTemplate() {
//        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient(),databaseName);
//        return mongoTemplate;
//    }
//
//    //After this configuring we can create MongoRepositories and use Mongo as primary database
//}