# Twitter Streaming Analyze using Java 8, Spark Streaming, Kafka,  
A demo project using Spark Streaming to analyze popular hashtags from twitter.
The data comes from the Twitter Streaming API source and is fed to Kafka.
The consumer com.twitter.producer.service receives data from Kafka and then processes it in a stream using Spark Streaming.


## Requirements
* Apache Maven 3.x
* JVM 8
* Docker machine
* Registered an Twitter Application. The following guides may also be helpful: [How to create a Twitter application.](http://docs.inboundnow.com/guide/create-twitter-application/)

## Quickstart guide
1. Change Twitter configuration in `\producer\src\main\resources\application.yml` with your API Key, client Id and Secret Id.

2. Run the kafka image using docker-compose(keep in mind that the kafka image need to pull zookeper too): 

```
~> docker-compose -f producer/src/main/docker/kafka-docker-compose.yml up -d   
```

3. Check if ZooKeeper and Kafka is running (from command prompt)

```
~> docker ps 
```

4. Run poducer and consumer app with:
```
~> mvn spring-boot:run
```



## References
* [Spring for Apache Kafka](https://projects.spring.io/spring-kafka/)
* [Spring Social Twitter](http://projects.spring.io/spring-social-twitter/)
* [Spark Overview](http://spark.apache.org/docs/latest/)
* [Apache Kafka Documentation](http://kafka.apache.org/documentation.html)
* [Big Data Processing with Apache Spark - Part 3: Spark Streaming](https://www.infoq.com/articles/apache-spark-streaming)
* [Spring Kafka - Embedded Unit Test Example](https://www.codenotfound.com/spring-kafka-embedded-unit-test-example.html)