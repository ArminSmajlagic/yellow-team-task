# Gaming Domain implemented with Spring Boot

## Description

Simple example of gaming industry problem solved in Spring Boot, messeging queues, SQL/NoSQL, Docker, variaty of protocoles and more.
I have been heavily relied on generics, abstraction & its implementation, dividing modules and organazing them to achive SOLID, DDD, DRY and TDD.
API will have few more updates before being considered compleat.
More details are avalable in the sections bellow :D

## Contents
- Layered architecture
- Achieving DDD by dividing project into presentation, application, domain & infrastructure layers
- Health metrics with Actuators + Tracing with Micrometer & Zipkin + Logging Aspect
- Advice for handling NotFound, InternalServerError, BadArgs exceptions + custom exceptions
- Redis In-Memory Caching of Application State & Redis Commander administration
- FlyWay migration written in SQL
- Object Mapper & Model Mapper for mapping json and objects
- PostgreSQL as primary database & PgAdmin for administration
- Redis, MongoDB, ElasticSearch as optional databases
- Apache Kafka & (Optional) RabbitMQ 
- Http requests, DTOs
- Swagger OpenAPI
- Base requests, base entity, base controller & service with generics(To achieve DRY code)
- Reading JSON data to performing data seeding and update data later with Apache Kafka
- Dockerization of databases, administration tools, caching solution...

<!--- ## Coming in a few days!

- gRPC services
- WebSockets 
- Kubernetis
- JWT
- Tests
- Gateway
- Redpanda
- Dockerization of Zookeaper & Kafka
-->

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
