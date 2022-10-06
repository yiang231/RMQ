package com.atguigu.rabbitmq.work;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer2 {
	public static void main(String[] args) throws IOException, TimeoutException {
		//创建连接工厂
		ConnectionFactory factory = new ConnectionFactory();
		//填写信息
		factory.setHost("192.168.139.139");
		factory.setPort(5672);
		factory.setVirtualHost("/");
		factory.setUsername("admin");
		factory.setPassword("123456");
		//创建链接
		Connection connection = factory.newConnection();
		//创建频道
		Channel channel = connection.createChannel();
		//创建消息队列
		channel.queueDeclare("work", true, false, false, null);
		//接受消息
		DefaultConsumer callback = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				System.out.println("consumerTag = " + consumerTag + ", envelope = " + envelope + ", body = " + new String(body));
			}
		};
		channel.basicConsume("work", true, callback);
	}
}
