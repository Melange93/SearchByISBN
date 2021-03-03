package com.reka.lakatos.booksofhungary.databasereader.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class DatabaseReaderRunnerApplication {
	@Value("${spring.data.mongodb.host}")
	private String mongodbHost;
	@Value("${spring.data.mongodb.port}")
	private String mongodbPort;
	@Value("${spring.data.mongodb.com.reka.lakatos.booksofhungary.database}")
	private String mongodbName;
	private final String DB_TYPE = "mongodb";

	public static void main(String[] args) {
		SpringApplication.run(DatabaseReaderRunnerApplication.class, args);
	}

	public @Bean MongoClient mongoClient() {
		final String clientConnection = DB_TYPE + "://" + mongodbHost + ":" + mongodbPort;
		return MongoClients.create(clientConnection);
	}

	public @Bean
	MongoTemplate mongoTemplate() {
		return new MongoTemplate(mongoClient(), mongodbName);
	}
}
