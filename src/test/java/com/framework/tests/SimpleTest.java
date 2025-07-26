package com.framework.tests;

import org.testng.annotations.Test;
import org.testng.Assert;
import com.framework.utils.ConfigBaseClass;

public class SimpleTest extends BaseTest {

    @Test
    public void testConfiguration() {
        // Test that configuration is loaded correctly
        Assert.assertEquals(ConfigBaseClass.USERNAME, "standard_user", "Username should be standard_user");
        Assert.assertEquals(ConfigBaseClass.PASSWORD, "secret_sauce", "Password should be secret_sauce");
        Assert.assertEquals(ConfigBaseClass.URL, "https://www.saucedemo.com/v1/", "URL should be correct");
        
        // Test that we can navigate to the page
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("saucedemo"), "Should be on saucedemo page");
        
        // Test that login was successful (we should be on inventory page)
        Assert.assertTrue(currentUrl.contains("inventory"), "Should be on inventory page after login");
    }
} 