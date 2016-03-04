package com.comeragh.examples.java_pipeline;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.jms.Connection;
import javax.jms.Session;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;
import org.apache.qpid.client.AMQConnection;
import org.apache.qpid.client.AMQAnyDestination;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class AMQPQueueSink implements Runnable
{
    private static final Logger logger = LogManager.getLogger(AMQPQueueSink.class);

    private final BlockingQueue<String> inboundQueue;
    private final Connection connection;
    private final Session session;
    private final Destination outboundQueue;
    private final MessageProducer producer;
    
    //private final String host;
    //private final int port;
    //private final String vhost;
    //private final String qname;

    AMQPQueueSink(BlockingQueue<String> in) throws Exception
    {
    	//Properties properties = new Properties();

    	inboundQueue = in;
    	connection = new AMQConnection("amqp://guest:guest@AMQPQueueSink/bdb?brokerlist='tcp://localhost:5672'");
    	session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    	//outboundQueue = new AMQAnyDestination("ADDR:message_queue; {create,always}");
    	outboundQueue = new AMQAnyDestination("test_queue");
    	producer = session.createProducer(outboundQueue);
    	//host = h;
    	//port = p;
    	//vhost = vh;
    	//qname = q;
    	//queue = in;
    	// TO DO
    }

    public void run()
    {
    	while(!Thread.currentThread().isInterrupted())
    	{
    		String s = null;
			try 
			{
				s = inboundQueue.poll(100, TimeUnit.MILLISECONDS);
			}
			catch (InterruptedException e)
			{
				return;
			}
    		if (s != null)
    		{
    			TextMessage message;
				try 
				{
					message = session.createTextMessage(s);
				}
				catch (JMSException e)
				{
					e.printStackTrace();
					return;
				}
    			try
    			{
    				logger.info("About to send message");
					producer.send(message);
					logger.info("Just sent message");
				}
    			catch (JMSException e) 
    			{
					e.printStackTrace();
				    return;
				}
    		}

    	}
    }
    
    public static void main(String[] args)
	{
		logger.info("Running main method");
	}
}
