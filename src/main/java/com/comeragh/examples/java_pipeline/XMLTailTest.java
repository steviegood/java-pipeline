package com.comeragh.examples.java_pipeline;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class XMLTailTest
{
    private static final Logger logger = LogManager.getLogger(XMLTailTest.class);
    private final LinkedBlockingQueue<String> fts2xap;
    private final LinkedBlockingQueue<String> xap2sos;
    private final Runnable fts;
    private final Runnable xap;
    private final Runnable sos;
    private final Executor executor;

    XMLTailTest(String fname)
    {
        fts2xap = new LinkedBlockingQueue<String>();
        xap2sos = new LinkedBlockingQueue<String>();
        fts = new FileTailerSource(fname, true, fts2xap);
        xap = new XMLAssemblerPipe(fts2xap,xap2sos);
        sos = new StdoutSink(xap2sos);
        executor = Executors.newCachedThreadPool();
    }
    
    void start()
    {
        executor.execute(fts);
        executor.execute(xap);
        executor.execute(sos);
    }
    
    public static void main(String[] args)
    {
        logger.info("Running main method");
        XMLTailTest stt = new XMLTailTest("/Users/steve/test.log");
        stt.start();
    }
}
