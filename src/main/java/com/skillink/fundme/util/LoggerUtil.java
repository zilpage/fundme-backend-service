package com.skillink.fundme.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;


public class LoggerUtil {

	private static final transient Log LOG = LogFactory.getLog(LoggerUtil.class);
	
    public static void logError(Logger logger, Exception ex){
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        logger.error(sw.toString());
        LOG.debug(sw.toString());
    }
    
    public static void debug(String in) {
    	LOG.debug(in);
    }
    public static void debugObject(Object in) {
    	LOG.debug(JSONMarshaller.marshall(in));
    }
    
    public static void debugObject(String msg, Object in) {
    	LOG.debug(JSONMarshaller.marshall(in));
    }
    
    
    public static void error(String in) {
    	LOG.error(in);
    }
    public static void errorObject(Object in) {
    	LOG.error(JSONMarshaller.marshall(in));
    }
    
    public static void errorObject(String msg, Object in) {
    	LOG.error(JSONMarshaller.marshall(in));
    }
    
    public static void errorObject(String message, Throwable e) {
    	LOG.error(message, e);
    }
    
    
 
}
