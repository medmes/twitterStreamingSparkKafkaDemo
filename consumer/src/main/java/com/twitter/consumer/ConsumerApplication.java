package com.twitter.consumer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsumerApplication implements CommandLineRunner {

    private final SparkConsumerService sparkConsumerService;

    public ConsumerApplication(SparkConsumerService sparkConsumerService) {
        this.sparkConsumerService = sparkConsumerService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        sparkConsumerService.run();
    }
}
