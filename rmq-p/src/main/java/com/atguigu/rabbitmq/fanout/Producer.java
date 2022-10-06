package com.atguigu.rabbitmq.fanout;

import com.rabbitmq.client.BuiltinExchangeType;
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
		//创建交换机
		String fanoutExchange = "fanoutExchange";
		channel.exchangeDeclare(fanoutExchange, BuiltinExchangeType.FANOUT, true, false, false, null);
		//创建队列
		channel.queueDeclare("queue1", true, false, false, null);
		channel.queueDeclare("queue2", true, false, false, null);
		//绑定交换机和队列
		channel.queueBind("queue1", fanoutExchange, "");
		channel.queueBind("queue2", fanoutExchange, "");
		//发送消息
		String msg = "hello world";
		channel.basicPublish(fanoutExchange, "", null, msg.getBytes());
		System.out.println("发送完毕");
		//关闭资源
		channel.close();
		connection.close();
	}
}
