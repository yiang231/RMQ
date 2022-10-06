package com.atguigu.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class RoutingListener1 implements MessageListener {
	@Override
	public void onMessage(Message message) {
		System.out.println(message.getMessageProperties().getDeliveryTag());
		System.out.println(new String(message.getBody()));
		System.out.println("============");
	}
}
