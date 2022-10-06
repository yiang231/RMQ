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
		System.out.println("simple消息发送完毕");
	}

	@Test
	public void work() {
		for (int i = 0; i < 10000; i++) {
			rabbitTemplate.convertAndSend("", "work", "hello world" + i);
			System.out.println("work消息发送完毕");
		}
	}

	@Test
	public void fanout() {
		for (int i = 0; i < 10000; i++) {
			rabbitTemplate.convertAndSend("fanoutExchange", "", "hello world" + i);
		}
		System.out.println("fanout消息发送完毕");
	}

	@Test
	public void routing() {
		String routingExchange = "routingExchange";

		String message = "hello rabbitmq!!!error......";
		rabbitTemplate.convertAndSend(routingExchange, "error", message);

		message = "hello rabbitmq!!! info ......";
		rabbitTemplate.convertAndSend(routingExchange, "info", message);

		message = "hello rabbitmq!!!  warning .....";
		rabbitTemplate.convertAndSend(routingExchange, "warning", message);

		message = "hello rabbitmq!!!  fatal .....";
		rabbitTemplate.convertAndSend(routingExchange, "fatal", message);

		System.out.println("routing消息发送完毕");
	}

	@Test
	public void topic() {
		String topicExchange = "topicExchange";

		String message = "hello rabbitmq!!! apple.orange.banana pear peach xigua......";
		rabbitTemplate.convertAndSend(topicExchange, "apple.orange.banana", message); //1

		message = "hello rabbitmq!!! dog cat tomcat netcat socat rabbit";
		rabbitTemplate.convertAndSend(topicExchange, "apple.orange.rabbit", message);//1,2

		message = "hello rabbitmq!!!  lazy hungry singleton ?????";
		rabbitTemplate.convertAndSend(topicExchange, "lazy", message);//???!!!! 2

		message = "hello rabbitmq!!!  lazy hungry singleton .......?????";
		rabbitTemplate.convertAndSend(topicExchange, "lazy.", message);//???!!!! 2

		message = "hello rabbitmq!!! lazy lazy lazy ....";
		rabbitTemplate.convertAndSend(topicExchange, "lazy.hungry.singleton", message); //2

		System.out.println("topic消息发送完毕");
	}
}