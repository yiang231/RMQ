package com.atguigu.rabbitmq.simple;

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
		//创建队列
		channel.queueDeclare("queue", true, false, false, null);
		//创建消息
		String msg = "Hello World12344431";
		//发送消息
		channel.basicPublish("", "queue", null, msg.getBytes());
		System.out.println("已发送消息" + msg);
		//关闭资源
		channel.close();
		connection.close();
	}
}
