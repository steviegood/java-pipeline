package com.comeragh.examples.java_pipeline;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class XMLToAMQPTest
{
    private static final Logger logger = LogManager.getLogger(XMLToAMQPTest.class);
    private final LinkedBlockingQueue<String> fts2xap;
    private final LinkedBlockingQueue<String> xap2ams;
    private final Runnable fts;
    private final Runnable xap;
    private final Runnable ams;
    private final Executor executor;

    XMLToAMQPTest(String fname) throws Exception
    {
        fts2xap = new LinkedBlockingQueue<String>();
        xap2ams = new LinkedBlockingQueue<String>();
        fts = new FileTailerSource(fname, true, fts2xap);
        xap = new XMLAssemblerPipe(fts2xap,xap2ams);
        ams = new AMQPQueueSink(xap2ams);
        executor = Executors.newCachedThreadPool();
    }
    
    void start()
    {
        executor.execute(fts);
        executor.execute(xap);
        executor.execute(ams);
    }
    
    public static void main(String[] args)
    {
        logger.info("Running main method");
        XMLToAMQPTest stt = null;
        try
        {
            stt = new XMLToAMQPTest("/Users/steve/test.log");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if (stt != null)
        {
            logger.info("Here we go!!");
            stt.start();
        }
    }
}
