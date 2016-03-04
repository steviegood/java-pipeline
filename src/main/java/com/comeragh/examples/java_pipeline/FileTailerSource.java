package com.comeragh.examples.java_pipeline;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.commons.io.input.Tailer;
import java.io.File;
import java.util.concurrent.BlockingQueue;

public class FileTailerSource implements Runnable
{
    private static final Logger logger = LogManager.getLogger(FileTailerSource.class);

    private final FileTailerListener listener;
    private final Boolean end;
    private final File file;
    
    FileTailerSource(String fname, Boolean fromEnd, BlockingQueue<String> out)
    {
    	end = fromEnd;
    	listener = new FileTailerListener(fname, end, out);
    	file = listener.getFile();
    }
    
    public void run()
    {
    	Tailer.create(file, listener, 100, end);
    }
    
	public static void main(String[] args)
	{
		logger.info("Running main method");
	}
}
