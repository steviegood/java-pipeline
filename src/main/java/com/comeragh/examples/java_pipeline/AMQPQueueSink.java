package com.comeragh.examples.java_pipeline;

import org.apache.commons.io.input.Tailer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class AMQPQueueSink implements Runnable
{
    private static final Logger logger = LogManager.getLogger(AMQPQueueSink.class);

    private final BlockingQueue<String> queue;
    //private final String host;
    //private final int port;
    //private final String vhost;
    //private final String qname;

    AMQPQueueSink(String propfilename, BlockingQueue<String> in)
    {
    	//host = h;
    	//port = p;
    	//vhost = vh;
    	//qname = q;
    	queue = in;
    	// TO DO
    }

    public void run()
    {
    	while(!Thread.currentThread().isInterrupted())
    	{
    		String s = null;
			try 
			{
				s = queue.poll(100, TimeUnit.MILLISECONDS);
			}
			catch (InterruptedException e)
			{
				return;
			}
    		if (s != null)
    			//ampq.put(s);
    			System.out.println("Blah");;
    	}
    }
    
    public static void main(String[] args)
	{
		logger.info("Running main method");
	}
}
