package org.example.chat;


import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
@Log4j2
public class RabbitMQConfig {
    @Value("${spring.rabbitmq.host}")
    private String rabbitmqHost;

    @Value("${spring.rabbitmq.username}")
    private String rabbitmqUsername;

    @Value("${spring.rabbitmq.password}")
    private String rabbitmqPassword;
    public static final String TOPIC_EXCHANGE_NAME = "topic2";

    public static final String ROUTING_KEY_SEND_MESSAGE = "chat.sendMessage";
    public static final String ROUTING_KEY_SEND_PHOTO = "chat.sendPhoto";
    public static final String ROUTING_KEY_ADD_USER = "chat.addUser";

    @Bean
    public Queue queueSendMessage() {
        return new Queue("chat-queue-send-message",true);
    }
    @Bean
    public Queue queueSendPhoto() {return new Queue("chat-queue-send-photo", true);}
    @Bean
    public Queue queueAddUser() {
        return new Queue("chat-queue-add-user", true);
    }

    @Bean
    public Binding bindingSendMessage(Queue queueSendMessage, TopicExchange topicExchange) {
        log.info(topicExchange.getType());
        return BindingBuilder.bind(queueSendMessage).to(topicExchange).with(ROUTING_KEY_SEND_MESSAGE);
    }

    @Bean
    public Binding bindingSendPhoto(Queue queueSendPhoto, TopicExchange topicExchange) {
        return BindingBuilder.bind(queueSendPhoto).to(topicExchange).with(ROUTING_KEY_SEND_PHOTO);
    }
    @Bean
    public Binding bindingAddUser(Queue queueAddUser, TopicExchange topicExchange) {
        return BindingBuilder.bind(queueAddUser).to(topicExchange).with(ROUTING_KEY_ADD_USER);
    }


    @Bean
    public TopicExchange topicExchange() {
        return ExchangeBuilder.topicExchange(TOPIC_EXCHANGE_NAME)
                .durable(true)
                .build();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitmqHost);
        connectionFactory.setUsername(rabbitmqUsername);
        connectionFactory.setPassword(rabbitmqPassword);
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }


    @Bean
    public RabbitTemplate rabbitTemplateSendMessage(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setRoutingKey("chat.sendMessage");
        rabbitTemplate.setExchange(TOPIC_EXCHANGE_NAME);
        return rabbitTemplate;
    }
    @Bean
    public RabbitTemplate rabbitTemplateSendPhoto(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setRoutingKey("chat.sendPhoto");
        rabbitTemplate.setExchange(TOPIC_EXCHANGE_NAME);
        return rabbitTemplate;
    }
    @Bean
    public RabbitTemplate rabbitTemplateAddUser(ConnectionFactory connectionFactory) {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setRoutingKey("chat.addUser");
        rabbitTemplate.setExchange(TOPIC_EXCHANGE_NAME);
        return rabbitTemplate;
    }
}