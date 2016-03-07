package com.comeragh.examples.java_pipeline;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class XMLAssemblerPipe implements Runnable
{
    private static final Logger logger = LogManager.getLogger(XMLAssemblerPipe.class);

    private final BlockingQueue<String> source;
    private final BlockingQueue<String> sink;
    private String xml;
    private Boolean insideXml;
    
    XMLAssemblerPipe(BlockingQueue<String> in, BlockingQueue<String> out)
    {
    	source = in;
    	sink = out;
    }
    
    public void run()
    {
    	String s;
    	xml = "";
    	insideXml = false;
    	while(!Thread.currentThread().isInterrupted())
    	{
			try 
			{
				s = source.poll(100, TimeUnit.MILLISECONDS);
			}
			catch (InterruptedException e) 
			{
				return;
			}
    		if (s != null)
    		{
    			if (!insideXml)
    			{
    				int i = s.indexOf("<?xml");
    				if (i>=0)
    				{
    					xml = xml+s.substring(i)+"\n";
    					insideXml = true;
    				}
    			}
    			else
    			{
    				xml = xml+s+"\n";
    				int i = s.indexOf("</FpML>");
    				if (i>=0)
    				{
    					try
    					{
							sink.put(xml);
						}
    					catch (InterruptedException e)
    					{
							return;
						}
    					xml = "";
    					insideXml = false;
    				}
    			}
    		}
    	}
    }
    
	public static void main(String[] args)
	{
		logger.info("Running main method");
	}
}
