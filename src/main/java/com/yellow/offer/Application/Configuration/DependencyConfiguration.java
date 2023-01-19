package com.yellow.offer.Application.Configuration;

import com.yellow.offer.Domain.Entities.Offer.Market.Market;
import com.yellow.offer.Domain.Services.Offer.Events.EventService;
import com.yellow.offer.Domain.Services.Offer.Markets.MarketOutcomeService;
import com.yellow.offer.Domain.Services.Offer.Markets.MarketService;
import com.yellow.offer.Infrastructure.Repositories.Offer.EventMarket.EventMarketOutcomeRepository;
import com.yellow.offer.Infrastructure.Repositories.Offer.EventMarket.EventMarketRepository;
import com.yellow.offer.Infrastructure.Repositories.Offer.Events.EventRepository;
import com.yellow.offer.Infrastructure.Repositories.Offer.Markets.MarketOutcomeRepository;
import com.yellow.offer.Infrastructure.Repositories.Offer.Markets.MarketRepository;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DependencyConfiguration {
    //Data source configuration properties
    @Value("${spring.datasource.url}")
    private String URL;
    @Value("${spring.datasource.username}")
    private String USER;
    @Value("${spring.datasource.password}")
    private String PASSWORD;
    @Value("${spring.datasource.driver-class-name}")
    private String DRIVER;

    //Kafka configuration property
    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaBootstrapServers;

    //Configuring kafka consumer & producer
    @Bean
    public Map<String, Object> producerConfig(){
        HashMap<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }
    @Bean
    public Map<String, Object> consumerConfig(){
        HashMap<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }
    @Bean
    public ProducerFactory<String, String> producerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }
    @Bean
    public ConsumerFactory<String, String> consumerFactory(){
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> listenerContainerFactory(ConsumerFactory<String, String> consumerFactory){
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }
    @Bean
    public NewTopic marketTopic(){
        return TopicBuilder.name("marketTopic").build();
    }
    @Bean
    public NewTopic eventTopic(){
        return TopicBuilder.name("eventTopic").partitions(2).build(); // eventTopic has to have at least 2 partitions
    }
    //Configuring data source
    @Bean
    DataSource dataSource() {
        // other option for data source specification
        //final DataSource ds = DataSourceBuilder.create().driverClassName(DRIVER).url(URL).username(USER).password(PASSWORD).build();

        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl(URL);
        driverManagerDataSource.setUsername(USER);
        driverManagerDataSource.setPassword(PASSWORD);
        driverManagerDataSource.setDriverClassName(DRIVER);
        return driverManagerDataSource;
    }
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
    //Configuring services & repositories
    @Bean
    public MarketRepository marketRepository() {
        return new MarketRepository(jdbcTemplate());
    }
    @Bean
    public MarketService marketService() {
        return new MarketService(marketRepository(), kafkaTemplate());
    }
    @Bean
    public MarketOutcomeRepository marketOutcomeRepository() {
        return new MarketOutcomeRepository();
    }
    @Bean
    public MarketOutcomeService marketOutcomeService() {
        return new MarketOutcomeService(marketOutcomeRepository());
    }
    @Bean
    public EventRepository eventRepository() {
        return new EventRepository(jdbcTemplate());
    }
    @Bean
    public EventService eventService() {
        return new EventService(eventRepository(), kafkaTemplate());
    }
    @Bean
    public EventMarketRepository eventMarketRepository() {
        return new EventMarketRepository();
    }
    @Bean
    public EventMarketOutcomeRepository eventMarketOutcomeRepository() {
        return new EventMarketOutcomeRepository();
    }
    //Configuring model mapper
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
