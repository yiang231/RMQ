package com.atguigu.rabbitmq.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
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
		//发布很多消息
		for (int i = 0; i < 10000; i++) {
			String msg = "hello world" + i;
			channel.basicPublish("", "work", null, msg.getBytes());
		}
		System.out.println("消息发送完成");
		//关闭资源
		channel.close();
		connection.close();
	}
}
