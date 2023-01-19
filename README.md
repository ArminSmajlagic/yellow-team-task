##  Yellow (TEAM) Task - NSoft 
<p>I suggest viewing the README.md in md viewer like one in GitHub or IntelliJ IDE</p>

## Description

The task is hopefully completed with all specified requirements. I have focused on writing good
code aside from incorporating many technologies. Project heavily depends on generics &
abstractions. I hope you will enjoy examining my work.

PS. When testing Kafka and zookeeper don't use containers from docker-compose.yml 
- I suggest when u test kafka and zookeeper to use zookeeper and kafka from original site using these instructions: https://kafka.apache.org/quickstart
- Download Zookeeper & Apache kafka from here: https://www.apache.org/dyn/closer.cgi?path=/kafka/3.3.1/kafka_2.13-3.3.1.tgz
- <b> OPTIONAL</b> : Modify the containers so that my application can connect to them 

## Contents
- Layered architecture
- Achieving DDD by dividing project into application,domain & infrastructure layers
- Health metrics with Actuators
- Tracing with Micrometer & Zipkin
- Logging Aspect
- Advice for handling NotFound, InternalServerError, BadArgs exceptions + custom exceptions
- Redis In-Memory Caching of Application State & Redis Commander administration
- FlyWay migration written in SQL
- Object Mapper & Model Mapper for mapping json and objects
- PostgreSQL as primary database & PgAdmin for administration
- Redis, MongoDB, ElasticSearch as optional databases
- Apache Kafka streaming of update data (Market topic with 1 partition & Event topic with 2 partitions)
- RabbitMQ as optional messaging queue 
- Unit of work with DTOs
- Every HTTP method has appropriate request
- Swagger OpenAPI
- Base requests, base entity, base controller & service with generics(To achieve DRY code)
- Reading JSON data and performing data seeding and update data streaming with Kafka producers
- Dockerization of databases, administration tools, caching solution...

## Sadly Not Included

- GRPC & WebSockets 
- FileSystems, MySql
- Redis streams

Hopefully I will get a chance to work with them to.

## Links

- Base application url: http://localhost:8080
- Swagger Documentation : http://localhost:8080/swagger-ui/index.html
- Actuators: http://localhost:8080/actuator
- Redis commander: http://localhost:8081
- Zipkin metrics : http://localhost:9411/zipkin
- PgAdmin : http://localhost:5050 (credentials are in docker-compose.yml)

These are commented out by default in docker-compose.yml
- Redpanda: http://localhost:8082 (Redpanda has not been configured because of kafka & zookeeper containers)
- MongoExpress: http://localhost:8083 
- RMQ Management plugin : http://localhost:15672 
- Redis as primary DB, MongoDB, Elasticsearch & RMQ are commented out by default as well
