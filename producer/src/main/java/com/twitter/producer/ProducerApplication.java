package com.twitter.producer;

import com.twitter.producer.service.TwitterStreamingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProducerApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ProducerApplication.class);

    private TwitterStreamingService twitterStreamingService;

    public ProducerApplication(TwitterStreamingService twitterStreamingService) {
        this.twitterStreamingService = twitterStreamingService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        logger.info("Running Twitter Streaming ...");
        twitterStreamingService.stream();
    }
}
