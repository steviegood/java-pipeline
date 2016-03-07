package com.comeragh.examples.java_pipeline;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class LineCountStdoutSink implements Runnable
{
    private static final Logger logger = LogManager.getLogger(LineCountStdoutSink.class);

    private final BlockingQueue<String> source;
    private final int logInterval;

    private int lineCount;
    private int linesCumulative;
    
    LineCountStdoutSink(BlockingQueue<String> in)
    {
        source = in;
        logInterval = 100;
    }
    
    LineCountStdoutSink(BlockingQueue<String> in, int logEvery)
    {
        source = in;
        logInterval = logEvery;
    }
    
    public void run()
    {
        String s;
        lineCount = 0;
        linesCumulative = 0;
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
                lineCount++;
                linesCumulative++;
                if (lineCount % logInterval == 0)
                {
                    logger.info("Processed "+lineCount+" messages ("+linesCumulative+ "total)");
                    lineCount = 0;
                }
            }
        }
    }
    
    public static void main(String[] args)
    {
        logger.info("Running main method");
    }
}
