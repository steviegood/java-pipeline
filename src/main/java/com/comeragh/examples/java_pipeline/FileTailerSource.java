package com.comeragh.examples.java_pipeline;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListenerAdapter;
import java.io.File;
import java.util.concurrent.BlockingQueue;

public class FileTailerSource extends TailerListenerAdapter
{
    private static final Logger logger = LogManager.getLogger(FileTailerSource.class);

    private final BlockingQueue<String> queue;
    private final String filename;
    private final File file;
    private final Tailer tailer;
    
    FileTailerSource(String fname, Boolean end, BlockingQueue<String> out)
    {
    	queue = out;
    	filename = fname;
    	file = new File(filename);
    	tailer = Tailer.create(file, this, 100, end);
    }

    public void handle(String line)
    {
    	try
    	{
        	queue.put(line);
        }
    	catch (InterruptedException ex)
    	{
    		tailer.stop();
    	}	
    }
    
	public static void main(String[] args)
	{
		logger.info("Running main method");
	}
}
