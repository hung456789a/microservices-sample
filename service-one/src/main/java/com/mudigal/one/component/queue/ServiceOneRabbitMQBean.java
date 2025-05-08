// Chắc chắn rồi. Đoạn code bạn đưa là một lớp cấu hình trong Spring Boot để tích hợp với RabbitMQ,
//  dùng để thiết lập các bean cần thiết cho việc gửi và nhận message trong một hệ thống microservices.
package com.mudigal.one.component.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

/**
 * RabbitMQ bean configurations
 * 
 * @author Vijayendra Mudigal
 *
 */
@Configuration //Đây là một lớp cấu hình Spring, nơi bạn định nghĩa các bean thủ công.
@Profile("!default") //Chỉ kích hoạt lớp này khi profile hiện tại KHÔNG phải là "default".
public class ServiceOneRabbitMQBean implements RabbitListenerConfigurer {
    
    public final static String queueName = "com.mudigal.microservices-sample.service-one";
    public final static String exchangeName = "com.mudigal.microservices-sample.services-exchange";
    public final static String routingKeyName = "com.mudigal.microservices-sample.service-*";
	// Tạo một queue bền vững (durable)
	//Tạo một topic exchange để định tuyến message đến queue dựa vào routing key.
    @Bean
    Queue queue() {
        return new Queue(queueName, true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }
    // Ràng buộc queue với exchange bằng một routing key (dùng wildcard *).
    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKeyName);
    }
	// RabbitTemplate: công cụ để gửi message từ Java application đến RabbitMQ.
	// Gắn Jackson2JsonMessageConverter để tự động convert object sang JSON và ngược lại.
    @Bean
	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
		return rabbitTemplate;
	}

	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	// Dùng cho việc nhận và chuyển đổi message JSON thành Java object.
	@Bean
	public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
		return new MappingJackson2MessageConverter();
	}
	// Cài đặt message converter cho các @RabbitListener method 
	//(listener sẽ dùng bean này để parse message JSON thành object Java).
	@Bean
	public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
		DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
		factory.setMessageConverter(consumerJackson2MessageConverter());
		return factory;
	}
	/// Nói với Spring Rabbit cách xử lý message listener bằng factory đã cấu hình.
	@Override
	public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
		registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
	}

}
