package com.twitter.consumer.config;

import org.apache.spark.SparkConf;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfig {

    @Bean
    public SparkConf sparkConf() {
        return new SparkConf()
                .setAppName("twitterKafkaWordCount")
                .setMaster("local[2]")
                .set("spark.executor.memory","1g");
    }
}
