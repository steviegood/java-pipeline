package com.comeragh.examples.java_pipeline;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PassthroughPipe implements Runnable
{
    private static final Logger logger = LogManager.getLogger(PassthroughPipe.class);

    private final BlockingQueue<String> source;
    private final BlockingQueue<String> sink;
    
    PassthroughPipe(BlockingQueue<String> in, BlockingQueue<String> out)
    {
        source = in;
        sink = out;
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
            {
                try
                {
                    sink.put(s);
                }
                catch (InterruptedException e) 
                {
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



