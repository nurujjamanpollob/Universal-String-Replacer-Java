package javadev.stringcollections.textreplacor.logutility;

import org.apache.logging.log4j.LogManager;

/**
 * @apiNote
 * Logger class that logs messages in a beautiful way. It usages the Log4j2 library to log messages.
 * <p>
 * The log format is below:
 * <p>
 *     <code>
 *         javadev.stringcollections.textreplacor.logutility.Logger.LogWarning() - Warning - Current Time - Message
 *         javadev.stringcollections.textreplacor.logutility.Logger.LogError() - Error - Current Time - Message
 *         javadev.stringcollections.textreplacor.logutility.Logger.LogInfo() - Info - Current Time - Message
 *         javadev.stringcollections.textreplacor.logutility.Logger.LogDebug() - Debug - Current Time - Message
 *         javadev.stringcollections.textreplacor.logutility.Logger.LogFatal() - Fatal - Current Time - Message
 *         javadev.stringcollections.textreplacor.logutility.Logger.LogTrace() - Trace - Current Time - Message
 *         javadev.stringcollections.textreplacor.logutility.Logger.LogException() - Exception - Current Time - Message
 *         javadev.stringcollections.textreplacor.logutility.Logger.LogCustom() - Custom - Current Time - Message
 *         </code>
 *         <p>
 *             <br>
 * If you use this class, a log file will create in this path: <b>current_working_directory/logs/universal-file-replacer-logs/file_replace.log</b>
 * @since 1.0
 * @version 1.0
 * @author nurujjamanpollob
 */
public class Logger {

    /**
     * Log warning message.
     * @param className class name
     * @param methodName method name
     * @param message message
     */
    public static void logWarning(Class<?> className, String methodName, String message) {
       org.apache.logging.log4j.Logger logger = LogManager.getLogger(className);


        logger.warn("{} [WARN] - {}", methodName, message);


    }

    /**
     * Log error message.
     * @param className class name
     * @param methodName method name
     * @param message message
     */
    public static void logError(Class<?> className, String methodName, String message) {
        org.apache.logging.log4j.Logger logger = LogManager.getLogger(className);

        logger.error("{} [ERROR] - {}", methodName, message);
    }

    /**
     * Log info message.
     * @param className class name
     * @param methodName method name
     * @param message message
     */
    public static void logInfo(Class<?> className, String methodName, String message) {
        org.apache.logging.log4j.Logger logger = LogManager.getLogger(className);

        logger.info("{} [INFO] - {}", methodName, message);
    }

    /**
     * Log debug message.
     * @param className class name
     * @param methodName method name
     * @param message message
     */
    public static void logDebug(Class<?> className, String methodName, String message) {
        org.apache.logging.log4j.Logger logger = LogManager.getLogger(className);

        logger.debug("{} [DEBUG] - {}", methodName, message);
    }

    /**
     * Log fatal message.
     * @param className class name
     * @param methodName method name
     * @param message message
     */
    public static void logFatal(Class<?> className, String methodName, String message) {
        org.apache.logging.log4j.Logger logger = LogManager.getLogger(className);

        logger.fatal("{} [FATAL] - {}", methodName, message);
    }

    /**
     * Log trace message.
     * @param className class name
     * @param methodName method name
     * @param message message
     */
    public static void logTrace(Class<?> className, String methodName, String message) {
        org.apache.logging.log4j.Logger logger = LogManager.getLogger(className);

        logger.trace("{} [TRACE] - {}", methodName, message);
    }

    /**
     * Log exception message.
     * @param className class name
     * @param methodName method name
     * @param message message
     */
    public static void logException(Class<?> className, String methodName, String message) {
        org.apache.logging.log4j.Logger logger = LogManager.getLogger(className);

        logger.error("{} [EXCEPTION] - {}", methodName, message);
    }

    /**
     * Log custom message.
     * @param className class name
     * @param methodName method name
     * @param message message
     */
    public static void logCustom(Class<?> className, String methodName, String message) {
        org.apache.logging.log4j.Logger logger = LogManager.getLogger(className);

        logger.info("{} [CUSTOM] - {}", methodName, message);
    }



}
