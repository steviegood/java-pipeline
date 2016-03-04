package com.comeragh.examples.java_pipeline;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class StdoutSink implements Runnable
{
	private static final Logger logger = LogManager.getLogger(StdoutSink.class);

    private final BlockingQueue<String> source;
    
    StdoutSink(BlockingQueue<String> in)
    {
        source = in;
    }
    
    public void run()
    {
    	String s;
    	while(!Thread.currentThread().isInterrupted())
    	{
    		try
    		{
    			s = source.poll(100, TimeUnit.MILLISECONDS);
    		}
    		catch (InterruptedException ex)
    		{ 
    		    return;
    	    }
    		if (s != null)
    			System.out.println(s);
    	}
    }
    
	public static void main(String[] args)
	{
		logger.info("Running main method");
	}
}
