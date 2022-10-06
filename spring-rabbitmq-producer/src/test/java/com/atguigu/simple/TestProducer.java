package com.atguigu.simple;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq.xml")
public class TestProducer {
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Test
	public void simple() {
		rabbitTemplate.convertAndSend("queue", "hello world");
	}
}
