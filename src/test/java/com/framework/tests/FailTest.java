package com.framework.tests;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import com.framework.pages.CartWorkflowPage;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;

public class FailTest extends BaseTest {
    private CartWorkflowPage cartPage;

    @BeforeMethod
    public void setUpCartPage() {
        cartPage = new CartWorkflowPage();
    }

    @Test
    @Description("JIRA-125:Test cart remove workflow functionality")
    public void testCartWorkflow() throws InterruptedException {

        Allure.step("Assert Fail", () -> {
            cartPage.clickContinueShopping();
        });
    }
}