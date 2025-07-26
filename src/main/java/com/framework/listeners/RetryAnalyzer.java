package com.framework.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import com.framework.utils.ConfigBaseClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Retry analyzer for failed tests
 * Implements TestNG's IRetryAnalyzer interface to retry failed tests
 */
public class RetryAnalyzer implements IRetryAnalyzer {
    
    private static final Logger logger = LogManager.getLogger(RetryAnalyzer.class);
    private int retryCount = 0;
    private final int maxRetryCount = ConfigBaseClass.RETRY_COUNT;
    
    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            logger.info("Retrying test: " + result.getName() + " - Attempt " + retryCount + " of " + maxRetryCount);
            return true;
        }
        logger.warn("Test failed after " + maxRetryCount + " retry attempts: " + result.getName());
        return false;
    }
    
    /**
     * Get the current retry count
     * @return current retry count
     */
    public int getRetryCount() {
        return retryCount;
    }
    
    /**
     * Get the maximum retry count from configuration
     * @return maximum retry count
     */
    public int getMaxRetryCount() {
        return maxRetryCount;
    }
} 