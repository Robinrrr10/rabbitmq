package com.github.rabbitmq;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * Hello world!
 *
 */
public class App 
{
	ConnectionFactory cFactory = new ConnectionFactory();
	String host = "192.168.40.116";
	Connection connection = null;
	Channel channel = null;
	
	private void connect() throws IOException, TimeoutException {
		cFactory.setHost(host);
		cFactory.setUsername("admin");
		cFactory.setPassword("admin");
		connection = cFactory.newConnection(); 
		channel = connection.createChannel();
	}
	
	private void close() throws IOException, TimeoutException {
		//channel.close();
		//connection.close();
	}
	
    public void sendMessage( String queueName, String exchangeName, String routingKey, String message ) throws IOException, TimeoutException
    {
        System.out.println( "Sending message:["+message+"] to queue:"+queueName);
        this.connect();
        channel.queueDeclare(queueName, false, false, false, null);
        channel.basicPublish(exchangeName, routingKey, null, message.getBytes());
        this.close();
        System.out.println("Message send");
    }
  
    public void receiveMessage(String queueName) throws IOException, TimeoutException
    {
    	System.out.println( "Receiving message from queue:"+queueName);
        this.connect();
        channel.queueDeclare(queueName, false, false, false, null);
        Consumer consumer = new DefaultConsumer(channel) {
        	@Override
        	public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws UnsupportedEncodingException {
        		String message = new String(body, "UTF-8");
        		System.out.println("Received message is:"+message);
        	}        	
        };
        channel.basicConsume(queueName, true, consumer);
        this.close();
        System.out.println("Message received");
    }
}
