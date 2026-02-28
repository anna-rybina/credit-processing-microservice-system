package com.github.annarybina.creditdecisionservice;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CreditDecisionServiceApplication {

	@Value("${credit.rabbitmq.queue}")
	private String queueName;

	public static void main(String[] args) {
		SpringApplication.run(CreditDecisionServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner initRabbit(AmqpAdmin amqpAdmin) {
		return args -> {
			Queue queue = new Queue(queueName, true);
			amqpAdmin.declareQueue(queue);
			System.out.println("=== QUEUE CREATED: " + queueName + " ===");
		};
	}
}
