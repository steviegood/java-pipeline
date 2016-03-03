package com.comeragh.examples.java_pipeline;

import org.apache.commons.io.input.Tailer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.BlockingQueue;

public class AMQPQueueSink implements Runnable
{
    private static final Logger logger = LogManager.getLogger(AMQPQueueSink.class);

    private final BlockingQueue<String> queue;

    AMQPQueueSink(, BlockingQueue<String> in)
    {
    	queue = in;

    }

    public static void main(String[] args)
	{
		logger.info("Running main method");
	}
}
