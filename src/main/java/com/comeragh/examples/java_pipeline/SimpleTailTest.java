package com.comeragh.examples.java_pipeline;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleTailTest
{
    private static final Logger logger = LogManager.getLogger(SimpleTailTest.class);
    private final LinkedBlockingQueue<String> queue;
    private final Runnable fts;
    private final Runnable sos;
    private final Executor executor;

    SimpleTailTest(String fname)
    {
        queue = new LinkedBlockingQueue<String>();
        fts = new FileTailerSource(fname, true, queue);
        sos = new StdoutSink(queue);
        executor = Executors.newCachedThreadPool();
    }
    
    void start()
    {
        executor.execute(fts);
        executor.execute(sos);
    }
    
    public static void main(String[] args)
    {
        logger.info("Running main method");
        SimpleTailTest stt = new SimpleTailTest("/Users/steve/test.log");
        stt.start();
    }
}
