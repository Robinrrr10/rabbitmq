package com.github.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.testng.annotations.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
 
	@Test(priority = 1)
	public void testProducer() throws IOException, TimeoutException {
		String queueName = "test-q3";
		String exchangeName = "test-ex";
		String routingKey = "bnd3";
		String message = "Message from code check eighth time";
		App app = new App();
		app.sendMessage(queueName, exchangeName, routingKey, message);
	}
	
	@Test(priority = 2)
	public void testConsumer() throws IOException, TimeoutException {
		String queueName = "test-q3";
		App app = new App();
		app.receiveMessage(queueName);
	}
	
}
