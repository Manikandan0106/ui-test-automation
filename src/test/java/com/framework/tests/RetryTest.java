package com.framework.tests;

import org.testng.annotations.Test;
import org.testng.Assert;
import io.qameta.allure.Description;

public class RetryTest extends BaseTest {

    private static int failureCount = 0;

    @Test
    @Description("Test to demonstrate retry functionality - will fail first 2 times then pass")
    public void testRetryFunctionality() {
        failureCount++;
        
        // This test will fail the first 2 times and pass on the 3rd attempt
        if (failureCount <= 2) {
           Assert.fail("Intentional failure for retry demonstration - Attempt " + failureCount);
        }
        
        // This will pass on the 3rd attempt
        Assert.assertTrue(true, "Test passed on attempt " + failureCount);
    }
} 