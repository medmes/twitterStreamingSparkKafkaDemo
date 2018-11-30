package com.twitter.consumer;

import com.twitter.consumer.config.KafkaConsumerConfig;
import com.twitter.consumer.config.KafkaProperties;
import com.twitter.consumer.util.HashTagsUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Collection;


@Service
public class SparkConsumerService {

    private final Logger log = LoggerFactory.getLogger(SparkConsumerService.class);

    private final SparkConf sparkConf;

    private final KafkaConsumerConfig kafkaConsumerConfig;

    private final KafkaProperties kafkaProperties;

    private final Collection<String> topics;

    public SparkConsumerService(SparkConf sparkConf, KafkaConsumerConfig kafkaConsumerConfig, KafkaProperties kafkaProperties) {
        this.sparkConf = sparkConf;
        this.kafkaConsumerConfig = kafkaConsumerConfig;
        this.kafkaProperties = kafkaProperties;
        this.topics = Arrays.asList(kafkaProperties.getTemplate().getDefaultTopic());
    }

    public void run() {
        log.debug("Running Spark Consumer Service..");

        // Create context with a 10 seconds batch interval
        JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, Durations.seconds(10));

        // Create direct kafka stream with brokers and topics
        JavaInputDStream<ConsumerRecord<String, String>> messages = KafkaUtils.createDirectStream(
                jssc,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.Subscribe(topics, kafkaConsumerConfig.consumerConfigs()));

        // Get the lines, split them into words, count the words and print
        JavaDStream<String> lines = messages.map(stringStringConsumerRecord -> stringStringConsumerRecord.value());
        //Count the tweets and print
        lines.count()
             .map(cnt -> "Popular hash tags in last 60 seconds (" + cnt + " total tweets):")
             .print();

        //
        lines.flatMap(text -> HashTagsUtils.hashTagsFromTweet(text))
             .mapToPair(hashTag -> new Tuple2<>(hashTag, 1))
             .reduceByKey((a, b) -> Integer.sum(a, b))
             .mapToPair(stringIntegerTuple2 -> stringIntegerTuple2.swap())
                .foreachRDD(rrdd -> {
                    log.info("---------------------------------------------------------------");
                    //Counts
                    rrdd.sortByKey(false).collect()
                            .forEach(record -> {
                                log.info(String.format(" %s (%d)", record._2, record._1));
                    });
                });

        // Start the computation
        jssc.start();
        try {
            jssc.awaitTermination();
        } catch (InterruptedException e) {
            log.error("Interrupted: {}", e);
            // Restore interrupted state...
        }
    }
}
